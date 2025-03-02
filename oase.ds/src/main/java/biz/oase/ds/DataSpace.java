/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Data Space
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved.
 * -------------------------------------------------------------------------- */

package biz.oase.ds;

/**
 * Represents a repository of the application data objects.
 *
 * @version 1.0.0 02.03.2025 15:56:23
 */
public interface DataSpace {

	/**
	 * @return the name of this data space
	 */
	String getName();

	/**
	 * Supplies access to a data space object.
	 * 
	 * @param aName   the unique name of the data container
	 * @return a newly created data container instance
	 */
	DSContainer createContainer(String aName);
}
