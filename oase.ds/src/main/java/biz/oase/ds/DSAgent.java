/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Dataspace Interface
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds;

import java.util.Iterator;
import java.util.function.Consumer;

import com.typesafe.config.Config;

import biz.car.csv.CSVRecord;

/**
 * Performs operations on a subset of the application data objects.
 *
 * @version 2.0.0 23.11.2025 16:11:35
 */
public interface DSAgent extends Consumer<Config> {

	/**
	 * Removes an entry from the dataspace.
	 * 
	 * @param aRecord the record representing the data entry.
	 * @return a <code>DSResult</code> object.
	 */
	DSResult delete(CSVRecord aRecord);

	/**
	 * Releases all allocated resources.
	 */
	void dispose();

	/**
	 * Inserts an new entry into the dataspace.
	 * 
	 * @param aRecord the record representing the data entry.
	 * @return the result containing the table entry related record.
	 */
	DSResult insert(CSVRecord aRecord);

	/**
	 * Runs a query on the application data space.
	 * 
	 * @return An iterator over the result set of the query
	 */
	Iterator<CSVRecord> query();

	/**
	 * Looks up a data entry.<br>
	 * The key values are taken from the fields in the given record.
	 * 
	 * @param aRecord the record holding the fields to use for the read operation.
	 * @return the result containing the user record.<br>
	 *         The read operation must return exactly one record.
	 */
	DSResult read(CSVRecord aRecord);

	/**
	 * Updates an entry in the dataspace.
	 * 
	 * @param aRecord the record representing the data entry.
	 * @return the result containing the user related record.
	 */
	DSResult update(CSVRecord aRecord);
}
