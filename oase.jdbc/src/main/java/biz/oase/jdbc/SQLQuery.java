/* --------------------------------------------------------------------------
 * Project: OASE JDBC
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.jdbc;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import biz.car.XRuntimeException;

/**
 * Represents a SQL statement for querying the database tables.<br>
 * The SQL must be a SQL SELECT statement.<br>
 * 
 * For executing a DDL, DML DCL statements use <code>SQLModify</code>.
 *
 * @version 2.0.0 03.03.2026 07:57:55
 */
public interface SQLQuery extends Supplier<SQLQueryData> {

	/**
	 * Releases all allocated resources.
	 */
	void dispose();

	/**
	 * Reads the SQL statement from the given file and executes it against the
	 * underlying database.
	 *
	 * @param aFile the path to the text file containing the SQL statement
	 * @return <code>true</code> if the query completed successfully
	 * @throws XRuntimeException if the file is empty or blank or is not a SELECT
	 *                           statement
	 */
	boolean execute(Path aFile);

	/**
	 * Reads the SQL statement given in <code>aScript</code> and executes it against
	 * the underlying database.
	 *
	 * @param aScript the text containing the SQL statement
	 * @return <code>true</code> if the query completed successfully
	 * @throws XRuntimeException if the script is empty or blank or is not a SELECT
	 *                           statement
	 */
	boolean execute(String aScript);

	/**
	 * @return the execution time in milliseconds
	 */
	long executionTimeMs();

	/**
	 * @return the error if the statement failed, empty otherwise
	 */
	Optional<Exception> failure();

	/**
	 * @return the list of labels for the columns in the result set
	 */
	List<String> labels();

	/**
	 * Moves to the next row in the result set.
	 * 
	 * @return <code>true</code> if end of result set not reached
	 */
	boolean next();
}
