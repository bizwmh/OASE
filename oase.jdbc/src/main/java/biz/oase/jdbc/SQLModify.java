/* --------------------------------------------------------------------------
 * Project: OASE JDBC
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.jdbc;

import java.nio.file.Path;

import biz.car.XRuntimeException;

/**
 * Represents a SQL statement for modifying the database tables.<br>
 * The SQL should be one of the following
 * <ul>
 * <li>a SQL DDL statement (like CREATE TABLE)
 * <li>a SQL DML statement (like INSERT)
 * <li>a SQL DCL statement (like GRANT)
 * </ul>
 * For executing a SQL SELECT use <code>SQLQuery</code>.
 * 
 * @version 2.0.0 02.03.2026 10:45:30
 */
public interface SQLModify {

	/**
	 * Reads the SQL statement from the given file and executes it against the
	 * underlying database.
	 *
	 * @param aFile the path to the text file containing the SQL statement
	 * @throws XRuntimeException if the file is empty or blank
	 */
	SQLResult execute(Path aFile);

	/**
	 * Reads the SQL statement given in <code>aScript</code> and executes it against
	 * the underlying database.
	 *
	 * @param aScript the text containing the SQL statement
	 * @throws XRuntimeException if the script is empty or blank
	 */
	SQLResult execute(String aScript);
}
