/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Dataspace Gateway
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds;

import biz.car.SYS;
import biz.car.util.ObjectRegistry;
import biz.oase.ds.bundle.MSG;

/**
 * The data space registry of the Application Client Service.
 *
 * @version 1.1.0 20.03.2025 10:24:38
 */
public class DSRegistry {

	private static ObjectRegistry<Dataspace> dsMap;

	static {
		dsMap = new ObjectRegistry<Dataspace>();
	}

	/**
	 * Supplies a registered data space.<br>
	 * <code>Dataspace</code>s are singleton instances.
	 * 
	 * @param aName the name of the data space
	 * @return the data space instance or <code>null</code> if no data space is
	 *         associated to the given name
	 */
	public static Dataspace get(String aName) {
		return dsMap.get(aName);
	}

	/**
	 * Registers a data space.
	 * 
	 * @param aDS the data space to register
	 */
	public static boolean register(Dataspace aDS) {
		String l_name = aDS.getName();

		if (dsMap.contains(l_name)) {
			SYS.LOG.error(MSG.DS_NOT_REGISTERED, l_name);
			return false;
		}
		dsMap.register(l_name, aDS);
		return true;
	}

	/**
	 * Unregisters a data space.
	 * 
	 * @param aDS the data space to unregister
	 */
	public static void unregister(Dataspace aDS) {
		String l_name = aDS.getName();

		dsMap.unregister(l_name);
	}

	/**
	 * Creates a default <code>DSRegistry</code> instance.
	 */
	private DSRegistry() {
		super();
	}
}
