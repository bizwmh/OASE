/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Data Space
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
 * @version 1.1.0 16.03.2023 03:51:08
 */
public class DSRegistry {

	private static ObjectRegistry<DataSpace> dsMap;

	/**
	 * Supplies a registered data space.<br>
	 * <code>DataSpace</code>s are singleton instances.
	 * 
	 * @param aName the name of the data space
	 * @return the data space instance or <code>null</code> if no data space is
	 *         associated to the given name
	 */
	public static DataSpace get(String aName) {
		return dsMap.get(aName);
	}

	/**
	 * Registers a data space.
	 * 
	 * @param aDS the data space to register
	 */
	public static boolean register(DataSpace aDS) {
		String l_name = aDS.getName();

		if (dsMap.contains(l_name)) {
			SYS.LOG.error(MSG.DS_NOT_REGISTERED, l_name);
			return false;
		}
		dsMap.register(l_name, aDS);
		return true;
	}

	static {
		dsMap = new ObjectRegistry<DataSpace>();
	}

	/**
	 * Creates a default <code>DSRegistry</code> instance.
	 */
	private DSRegistry() {
		super();
	}
}
