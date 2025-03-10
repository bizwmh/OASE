/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Dataspace Gateway
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved.
 * -------------------------------------------------------------------------- */

package biz.oase.ds;

/**
 * Represents a collection of application data objects.
 *
 * @version 1.0.0 02.03.2025 15:56:23
 */
public interface DataSpace {

	/**
	 * @return the name of this data space
	 */
	String getName();

	/**
	 * Supplies access to a data space resource.
	 * 
	 * @param aName   the unique name of the resource
	 * @return a newly created data resource
	 */
	DSResource getResource(String aName);
}
