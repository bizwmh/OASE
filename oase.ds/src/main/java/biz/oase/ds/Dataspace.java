/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Dataspace Interface
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds;

import biz.car.util.Identifiable;
import biz.car.util.ObjectRegistry;

/**
 * The persistent storage of the application data objects.
 *
 * @version 2.0.0 23.11.2025 16:09:23
 */
public interface Dataspace extends Identifiable {

	/**
	 * The central registry for dataspaces.
	 */
	ObjectRegistry<Dataspace> Registry = new ObjectRegistry<Dataspace>();

	/**
	 * Get a reference to the dataspace agent with the given name.
	 * 
	 * @param aName the name of the agent
	 * @return the DSAgent instance or <code>null</code>
	 */
	DSAgent getAgent(String aName);
}
