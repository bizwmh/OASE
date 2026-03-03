/* --------------------------------------------------------------------------
 * Project: OASE JDBC
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.jdbc;

import java.nio.file.Path;

import biz.car.XRuntimeException;

/**
 * Represents a SQL statement for querying the database tables.<br>
 * The SQL must be a SQL SELECT statement.<br>
 * 
 * For executing a DDL, DML DCL statements use <code>SQLModify</code>.
 *
 * @version 2.0.0 03.03.2026 07:57:55
 */
public interface SQLQuery {

	/**
	 * Reads the SQL statement from the given file and executes it against the
	 * underlying database.
	 *
	 * @param aFile the path to the text file containing the SQL statement
	 * @return the cursor to traverse the result set
	 * @throws XRuntimeException if the file is empty or blank or is not a SELECT
	 *                           statement
	 */
	SQLCursor execute(Path aFile);

	/**
	 * Reads the SQL statement given in <code>aScript</code> and executes it against
	 * the underlying database.
	 *
	 * @param aScript the text containing the SQL statement
	 * @return the cursor to traverse the result set
	 * @throws XRuntimeException if the script is empty or blank or is not a SELECT
	 *                           statement
	 */
	SQLCursor execute(String aScript);
}
