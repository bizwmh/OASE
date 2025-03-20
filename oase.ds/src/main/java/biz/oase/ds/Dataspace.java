/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Dataspace Interface
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved.
 * -------------------------------------------------------------------------- */

package biz.oase.ds;

/**
 * Represents a collection of application data objects.
 *
 * @version 1.0.0 20.03.2025 10:12:52
 */
public interface Dataspace {

	/**
	 * @return the name of this data space
	 */
	String getName();

	/**
	 * Supplies access to a data space table.
	 * 
	 * @param aName   the unique name of the table
	 * @return a newly created table resource
	 */
	DSTable getTable(String aName);
}
