/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm;

import biz.car.XRuntimeException;
import biz.car.csv.CSVRecord;

/**
 * Provides write access for application data objects.
 *
 * @version 2.0.0 28.10.2025 12:32:13
 */
public interface SMOutput {

	/**
	 * @return the current procedure context
	 */
	SMContext context();

	/**
	 * @return the current CSV record which is to be written to the output channel.
	 */
	CSVRecord getCurrent();

	/**
	 * Writes the cached CSV record to the receiving output channel.
	 * 
	 * @throws XRuntimeException if the write operation fails
	 */
	void write();

	/**
	 * Sends the CSV record to the receiving output channel.
	 * 
	 * @param aRecord the record to be written
	 * @throws XRuntimeException if the write operation fails
	 */
	void write(CSVRecord record);
}
