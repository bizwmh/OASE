/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Dataspace Gateway
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds;

import biz.car.io.DataRecord;

/**
 * Facade interface for data space operations.<br>
 * This interface is related to exactly one data space table.
 *
 * @version 1.0.0 22.03.2025 16:14:06
 */
public interface DSAPI {

	/**
	 * Closes the connection to the data space table.<br>
	 * Allocated resources are released.
	 */
	void close();

	/**
	 * Removes an entry from the table.
	 * 
	 * @param aRecord the record representing the table's data entry.
	 * @return a <code>DSResult</code> object.
	 */
	DSResult delete(DataRecord aRecord);

	/**
	 * Inserts an new entry into the table.
	 * 
	 * @param aRecord the record representing the table's data entry.
	 * @return the result containing the table entry related record.
	 */
	DSResult insert(DataRecord aRecord);

	/**
	 * Creates a connection to the data space table.
	 */
	void open();

	/**
	 * Looks up a data entry from the table.<br>
	 * The key values are taken from the fields in the given record.
	 * 
	 * @param aRecord the record holding the fields to use for the read
	 *                operation.
	 * @return the result containing the user record.<br>
	 *         The read operation must return exactly one record.
	 */
	DSResult read(DataRecord aRecord);

	/**
	 * Updates an entry in the table.
	 * 
	 * @param aRecord the record representing the table's data entry.
	 * @return the result containing the user related record.
	 */
	DSResult update(DataRecord aRecord);
}
