/* --------------------------------------------------------------------------
 * Project: OASE JDBC
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.jdbc.core;

import biz.oase.jdbc.Column;

/**
 * Implements the column of a database table.
 *
 * @version 2.0.0 27.02.2026 16:00:07
 */
public class TableColumn implements Column {

	public final String name;
	public final DBTable table;

	private int size;
	private String type;

	/**
	 * Creates a default <code>TableColumn</code> instance.
	 * 
	 * @param aName  the name of the column
	 * @param aTable the table this column belongs to
	 */
	public TableColumn(String aName, DBTable aTable) {
		super();

		name = aName;
		table = aTable;
	}

	/**
	 * @return the data type of this column
	 */
	public String dataType() {
		return type;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int size() {
		return size;
	}

	/**
	 * Sets the data type of this column.
	 * 
	 * @param aType the string value for the data type
	 */
	void setDataType(String aType) {
		type = aType;
	}

	/**
	 * Sets the column size.
	 * 
	 * @param aSize the size of this column
	 */
	void setSize(int aSize) {
		size = aSize;
	}
}
