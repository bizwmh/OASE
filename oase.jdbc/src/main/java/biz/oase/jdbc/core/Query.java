/* --------------------------------------------------------------------------
 * Project: OASE JDBC
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.jdbc.core;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import biz.car.XRuntimeException;
import biz.oase.jdbc.SQLQuery;
import biz.oase.jdbc.SQLQueryData;
import biz.oase.jdbc.bundle.MSG;

/**
 * Represents a SQL SELECT statement for querying the database tables.<br>
 *
 * @version 2.0.0 03.03.2026 17:49:26
 */
public class Query implements SQLQuery {

	private long executionTimeMs;
	private Optional<Exception> failure;
	private List<String> labels;
	private Connection myConnection;
	private DB myDB;
	private ResultSet result;
	private Statement stmt;

	/**
	 * Creates a default <code>Query</code> instance.
	 * 
	 * @param aDB
	 */
	public Query(DB aDB) {
		super();

		myDB = aDB;
		failure = Optional.empty();
		labels = new ArrayList<String>();
	}

	@Override
	public void dispose() {
		if (myConnection != null) {
			try {
				result.close();
				stmt.close();
				myConnection.close();
				labels.clear();
			} catch (SQLException anEx) {
				myDB.warn(MSG.CONNECTION_NOT_CLOSED, myDB.getName());
			} finally {
				labels = null;
				myConnection = null;
				result = null;
				stmt = null;
			}
		}
	}

	@Override
	public boolean execute(Path aFile) {
		long l_start = System.currentTimeMillis();

		try {
			String l_sql = Files.readString(aFile);
			boolean l_ret = execute(l_sql);
			executionTimeMs = System.currentTimeMillis() - l_start;

			return l_ret;
		} catch (IOException anEx) {
			executionTimeMs = System.currentTimeMillis() - l_start;
			failure = Optional.of(anEx);

			return false;
		}
	}

	@Override
	public boolean execute(String aScript) {
		long l_start = System.currentTimeMillis();
		String l_sql = aScript.strip();

		if (l_sql.isBlank()) {
			executionTimeMs = System.currentTimeMillis() - l_start;
			Exception l_ex = new XRuntimeException(MSG.SQL_STATEMENT_EMPTY);
			failure = Optional.of(l_ex);

			return false;
		}
		try (Connection l_conn = myDB.getDataSource().getConnection()) {
			Statement l_stmt = l_conn.createStatement();
			result = l_stmt.executeQuery(l_sql);
			executionTimeMs = System.currentTimeMillis() - l_start;
			ResultSetMetaData l_meta = result.getMetaData();

			for (int i = 1; i <= l_meta.getColumnCount(); i++) {
				labels.add(l_meta.getColumnLabel(i));
			}
			return true;
		} catch (SQLException anEx) {
			executionTimeMs = System.currentTimeMillis() - l_start;
			failure = Optional.of(anEx);

			return false;
		}
	}

	@Override
	public long executionTimeMs() {
		return executionTimeMs;
	}

	@Override
	public Optional<Exception> failure() {
		return failure;
	}

	@Override
	public SQLQueryData get() {
		try {
			ResultSetMetaData l_meta = result.getMetaData();
			QueryData l_ret = new QueryData();

			for (int i = 1; i <= l_meta.getColumnCount(); i++) {
				String l_name = l_meta.getColumnLabel(i); // AS-Alias bevorzugen
				int l_type = l_meta.getColumnType(i); // java.sql.Types
				Object l_value = readTyped(result, i, l_type);

				l_ret.add(l_name, l_value);
			}
			return l_ret;
		} catch (SQLException anEx) {
			throw myDB.exception(anEx);
		}
	}

	@Override
	public List<String> labels() {
		return List.copyOf(labels);
	}

	@Override
	public boolean next() {
		try {
			boolean l_ret = result.next();

			return l_ret;
		} catch (SQLException anEx) {
			throw myDB.exception(anEx);
		}
	}

	/**
	 * TODO readTyped
	 * 
	 * @param result2
	 * @param i
	 * @param l_type
	 * @return
	 */
	private static Object readTyped(ResultSet rs, int col, int sqlType)
	        throws SQLException {

	    return switch (sqlType) {
	        case Types.INTEGER, Types.SMALLINT, Types.TINYINT -> {
	            int v = rs.getInt(col); yield rs.wasNull() ? null : v;
	        }
	        case Types.BIGINT -> {
	            long v = rs.getLong(col); yield rs.wasNull() ? null : v;
	        }
	        case Types.FLOAT, Types.REAL -> {
	            float v = rs.getFloat(col); yield rs.wasNull() ? null : v;
	        }
	        case Types.DOUBLE -> {
	            double v = rs.getDouble(col); yield rs.wasNull() ? null : v;
	        }
	        case Types.NUMERIC, Types.DECIMAL ->
	            rs.getBigDecimal(col);          // gibt selbst null zurück
	        case Types.BOOLEAN, Types.BIT -> {
	            boolean v = rs.getBoolean(col); yield rs.wasNull() ? null : v;
	        }
	        case Types.DATE ->
	            rs.getDate(col);                // null-sicher
	        case Types.TIMESTAMP, Types.TIMESTAMP_WITH_TIMEZONE ->
	            rs.getTimestamp(col);
	        case Types.TIME ->
	            rs.getTime(col);
	        case Types.BLOB ->
	            rs.getBytes(col);
	        default ->                          // CHAR, VARCHAR, CLOB, etc.
	            rs.getString(col);
	    };
	}}
