/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Dataspace Interface
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds;

/**
 * Represents a data space table
 *
 * @version 1.0.0 20.03.2025 10:13:20
 */
public interface DSTable {

	/**
	 * TODO create
	 * @return
	 */
	DSResult create(DSRecord aRecord);
	
	/**
	 * TODO create
	 * @return
	 */
	DSResult delete(DSRecord aRecord);
	
	/**
	 * Performs a query on the data space table.
	 * 
	 * @return the result set of the query
	 */
	DSResultSet query();
	
	/**
	 * TODO create
	 * @return
	 */
	DSResult read(DSRecord aRecord);
	
	/**
	 * TODO create
	 * @return
	 */
	DSResult update(DSRecord aRecord);
}
