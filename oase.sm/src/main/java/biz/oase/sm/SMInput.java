/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm;

import biz.car.csv.CSVRecord;
import biz.oase.car.SEObject;

/**
 * Provides read access to the application data objects.
 *
 * @version 2.0.0 30.03.2026 10:31:03
 */
public interface SMInput extends SEObject {

	/**
	 * @return the current procedure context
	 */
	SMContext context();

	/**
	/**
	 * @return the current CSV record of this input channel.
	 */
	CSVRecord getCurrent();
}
