/* --------------------------------------------------------------------------
 * Project: OASE JDBC
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.jdbc;

import biz.car.XRuntimeException;

/**
 * Represents a row in a database table.
 *
 * @version 2.0.0 03.03.2026 08:29:49
 */
public interface SQLResultData {

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * {@code ResultSet} object as a {@code String} in the Java programming
	 * language.
	 *
	 * @param aIndex the first column is 1, the second is 2, ...
	 * @return the column value; if the value is SQL {@code NULL}, the value
	 *         returned is {@code null}
	 * @throws XRuntimeException if the aIndex is not valid; if a database access
	 *                           error occurs or this method is called on a closed
	 *                           result set
	 */
	String getString(int aIndex);

	/**
	 * Retrieves the value of the designated column in the current row of this
	 * {@code ResultSet} object as a {@code String} in the Java programming
	 * language.
	 *
	 * @param aLabel the label for the column specified with the SQL AS clause. If
	 *               the SQL AS clause was not specified, then the label is the name
	 *               of the column
	 * @return the column value; if the value is SQL {@code NULL}, the value
	 *         returned is {@code null}
	 * @throws XRuntimeException if the columnLabel is not valid; if a database
	 *                           access error occurs or this method is called on a
	 *                           closed result set
	 */
	String getString(String aLabel);
}
