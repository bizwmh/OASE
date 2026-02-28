/* --------------------------------------------------------------------------
 * Project: OASE Service API
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.service.jdbc;

import java.util.List;

/**
 * Represents a table within a database.
 *
 * @version 2.0.0 27.02.2026 15:43:54
 */
public interface Table {

	/**
	 * @return the list of the names of the table columns
	 */
	List<String> columnNames();

	/**
	 * Provides a table column.
	 * 
	 * @param aName the name of the column.
	 * @return the {@link Column} instance or <code>null</code>
	 */
	Column getColumn(String aName);

	/**
	 * @return the name of the table.
	 */
	String getName();

	/**
	 * Checks if a column exists for a given name.
	 * 
	 * @param aColumn the name of the column
	 * @return <code>true</code> if a columns with the given name is defined
	 */
	boolean hasColumn(String aColumn);
}
