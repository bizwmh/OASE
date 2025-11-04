/* --------------------------------------------------------------------------
 * Project: XXX
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm;

import biz.car.csv.CSVRecord;

/**
 * TODO SMOutput comment
 *
 * @version 1.0.0 28.10.2025 12:32:13
 */
public interface SMOutput {

	/**
	 * TODO getCurrent
	 * @return
	 */
	CSVRecord getCurrent();

	/**
	 * TODO write
	 */
	void write();

	/**
	 * TODO write
	 * @param record
	 */
	void write(CSVRecord record);

	SMContext context();
}
