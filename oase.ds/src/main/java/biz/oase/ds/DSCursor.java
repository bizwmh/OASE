/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Dataspace Interface
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds;

import biz.car.config.Configurable;
import biz.car.csv.CSVRecord;

/**
 * An iterator over the result set of a data space query.
 *
 * @version 2.0.0 18.11.2025 14:32:32
 */
public interface DSCursor extends Configurable {

	/**
	 * Releases all allocated resources.
	 */
	void dispose();
	
	/**
	 * @return the next entry of the result set or <code>null</code>
	 */
	CSVRecord next();
	
	/**
	 * Opens the cursor and creates the result set.
	 */
	void open();
}
