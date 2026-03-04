/* --------------------------------------------------------------------------
 * Project: OASE JDBC
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.jdbc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import biz.car.XRuntimeException;

/**
 * Represents a row in a database table.
 *
 * @version 2.0.0 03.03.2026 08:29:49
 */
public interface SQLQueryData {
//
//	/**
//	 * Retrieves the value of the designated column in the current row of this
//	 * {@code ResultSet} object as a {@code String} in the Java programming
//	 * language.
//	 *
//	 * @param aIndex the first column is 1, the second is 2, ...
//	 * @return the column value; if the value is SQL {@code NULL}, the value
//	 *         returned is {@code null}
//	 * @throws XRuntimeException if the aIndex is not valid; if a database access
//	 *                           error occurs or this method is called on a closed
//	 *                           result set
//	 */
//	String getString(int aIndex);

	/**
	 * TODO getBigDecimal
	 * 
	 * @param aLabel
	 * @return
	 */
	BigDecimal getBigDecimal(String aLabel);

	/**
	 * TODO getBoolean
	 * 
	 * @param aLabel
	 * @return
	 */
	boolean getBoolean(String aLabel);

	/**
	 * TODO getDate
	 * 
	 * @param aLabel
	 * @return
	 */
	LocalDate getDate(String aLabel);

	/**
	 * TODO getDouble
	 * 
	 * @param aLabel
	 * @return
	 */
	double getDouble(String aLabel);

	/**
	 * TODO getInt
	 * 
	 * @param aLabel
	 * @return
	 */
	int getInt(String aLabel);

	/**
	 * TODO getLong
	 * 
	 * @param aLabel
	 * @return
	 */
	long getLong(String aLabel);

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

	/**
	 * TODO getTimestamp
	 * 
	 * @param aLabel
	 * @return
	 */
	LocalDateTime getTimestamp(String aLabel);

	/** Typsicherer Rohzugriff – Aufrufer castet selbst */
	/**
	 * TODO getValue
	 * 
	 * @param aLabel
	 * @return
	 */
	Object getValue(String aLabel);

	/**
	 * @return the list of labels for a single row
	 */
	List<String> labels();
}
