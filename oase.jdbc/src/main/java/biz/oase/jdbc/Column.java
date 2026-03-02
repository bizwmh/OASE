/* --------------------------------------------------------------------------
 * Project: OASE JDBC
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.jdbc;

/**
 * Represents a column witnin a table.
 *
 * @version 2.0.0 27.02.2026 15:47:37
 */
public interface Column {

	/**
	 * @return the data type of this column
	 */
	String getDataType();

	/**
	 * @return the name of the column.
	 */
	String getName();

	/**
	 * @return the size of this column
	 */
	int getSize();
}
