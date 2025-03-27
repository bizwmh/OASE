/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Dataspace Interface
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds;

import biz.car.config.Configurable;

/**
 * Represents a data space table
 *
 * @version 1.0.0 20.03.2025 10:13:20
 */
public interface DSTable extends Configurable {

	/**
	 * @return a reference to the API implementation
	 */
	DSAPI getAPI();

	/**
	 * @return the name of this table
	 */
	String getName();
	
	/**
	 * Performs a query on the data space table.
	 * 
	 * @return the result set of the query
	 */
	DSCursor query();
}
