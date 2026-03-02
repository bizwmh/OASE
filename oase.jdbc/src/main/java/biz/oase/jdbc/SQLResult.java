/* --------------------------------------------------------------------------
 * Project: OASE JDBC
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.jdbc;

import java.util.Optional;

/**
 * Represents the result of a SQL statement execution.
 *
 * @version 2.0.0 02.03.2026 10:48:11
 */
public interface SQLResult {

	/**
	 * @return the execution time in milliseconds
	 */
	long executionTimeMs();

	/**
	 * @return the error if the statement failed, empty otherwise
	 */
	Optional<Exception> failure();

	/**
	 * @return <code>true</code> if the statement executed without error
	 */
	boolean isOK();

	/**
	 * @return the {@link ResultData} for SELECT statements, empty otherwise
	 */
	Optional<SQLResultData> resultData();

	/**
	 * @return the number of affected rows for DML statements, -1 otherwise
	 */
	int updateCount();
}
