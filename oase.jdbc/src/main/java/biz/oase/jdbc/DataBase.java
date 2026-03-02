/* --------------------------------------------------------------------------
 * Project: OASE JDBC
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.jdbc;

import java.util.List;

import javax.sql.DataSource;

/**
 * Represents a JDBC database.
 *
 * @version 2.0.0 27.02.2026 15:42:16
 */
public interface DataBase {

	/**
	 * @return a reference to the underlying JDBC datasource.
	 */
	DataSource getDataSource();

	/**
	 * @return the name of the database.
	 */
	String getName();

	/**
	 * @param aTable the unique name of the table
	 * @return the <code>Table</code> instance or <code>null</code> if a table with
	 *         the given name does not exist
	 */
	Table getTable(String aTable);

	/**
	 * Checks if a table exists for a given name.
	 * 
	 * @param aTable the unique name of the table
	 * @return <code>true</code> if a table with the given name is defined
	 */
	boolean hasTable(String aTable);

	/**
	 * @return a new {@link SQLStatement} backed by this database.
	 */
	SQLStatement sqlStatement();

	/**
	 * @return the list of names for the tables defined in this database
	 */
	List<String> tableNames();

}
