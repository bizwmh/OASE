/* --------------------------------------------------------------------------
 * Project: OASE JDBC
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.jdbc;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * Represents the result set of a SQLQuery statement execution.
 *
 * @version 2.0.0 03.03.2026 08:16:16
 */
public interface SQLCursor extends Supplier<SQLResultData> {

	/**
	 * Releases all allocated resources.
	 */
	void dispose();
	
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
	 * Moves to the next row in the result set.
	 * 
	 * @return <code>true</code> if end of result set not reached
	 */
	 boolean next();
}
