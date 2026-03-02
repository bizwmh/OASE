/* --------------------------------------------------------------------------
 * Project: OASE JDBC
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.jdbc.core;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import biz.car.SYS;
import biz.oase.jdbc.Column;
import biz.oase.jdbc.JDBC;
import biz.oase.jdbc.Table;

/**
 * Represents a database table.
 *
 * @version 2.0.0 27.02.2026 16:01:51
 */
public class DBTable implements JDBC, Table {

	private Map<String, Column> columnMap;
	private DB myDB;
	private String name;

	/**
	 * Creates a default <code>DBTable</code> instance.
	 */
	public DBTable(String aName, DB aClient) {
		super();

		name = aName;
		myDB = aClient;
		columnMap = new HashMap<String, Column>();

		loadColumns();
	}

	@Override
	public List<String> columnNames() {
		return columnMap.keySet()
		      .stream()
		      .toList();
	}

	@Override
	public Column getColumn(String aName) {
		return columnMap.get(aName);
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public boolean hasColumn(String aColumn) {
		return columnMap.containsKey(aColumn);
	}

	private synchronized void loadColumns() {
		try (Connection l_conn = myDB.getDataSource().getConnection()) {
			DatabaseMetaData l_meta = l_conn.getMetaData();
			ResultSet l_columns = l_meta.getColumns(
			      null, // catalog (null = alle)
			      null, // schema (null = alle, oder z.B. "PUBLIC")
			      name, // tabellenname (exakt oder mit Wildcards wie "%")
			      null // spaltenname-filter (null = alle)
			);
			while (l_columns.next()) {
				String l_name = l_columns.getString(COLUMN_NAME);
				String l_type = l_columns.getString(TYPE_NAME);
				int l_size = l_columns.getInt(COLUMN_SIZE);
				TableColumn l_col = new TableColumn(l_name, this);

				l_col.setDataType(l_type);
				l_col.setSize(l_size);
				columnMap.put(l_name, l_col);
			}
		} catch (SQLException anEx) {
			throw SYS.LOG.exception(anEx);
		}
	}
}
