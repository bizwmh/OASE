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
import java.sql.SQLException;
import java.sql.Statement;

import biz.car.XRuntimeException;
import biz.oase.jdbc.SQLModify;
import biz.oase.jdbc.SQLResult;
import biz.oase.jdbc.bundle.MSG;

/**
 * Implements the execution of a SQL DDL, DCL or DML statement.
 *
 * @version 2.0.0 03.03.2026 17:48:04
 */
public class Modify implements SQLModify {

	private DB myDB;

	/**
	 * Creates a default <code>SQLExecutor</code> instance.
	 * 
	 * @param aDB the underlying database
	 */
	public Modify(DB aDB) {
		super();

		myDB = aDB;
	}

	@Override
	public SQLResult execute(Path aFile) {
		long l_start = System.currentTimeMillis();

		try {
			String l_sql = Files.readString(aFile);

			return execute(l_sql);
		} catch (IOException anEx) {
			long l_elapsed = System.currentTimeMillis() - l_start;
			return Result.failure(l_elapsed, anEx);
		}
	}
	
	@Override
	public SQLResult execute(String aScript) {
		long l_start = System.currentTimeMillis();
		String l_sql = aScript.strip();
		
		if (l_sql.isBlank()) {
			long l_elapsed = System.currentTimeMillis() - l_start;
			return Result.failure(l_elapsed, new XRuntimeException(MSG.SQL_STATEMENT_EMPTY));
		}
		try (Connection l_conn = myDB.getDataSource().getConnection()) {
			Statement l_stmt = l_conn.createStatement();
			long l_elapsed = System.currentTimeMillis() - l_start;
			int l_count = l_stmt.executeUpdate(l_sql);
			
			return Result.success(l_elapsed, l_count);
		} catch (SQLException anEx) {
			long l_elapsed = System.currentTimeMillis() - l_start;
			return Result.failure(l_elapsed, anEx);
		}
	}
}
