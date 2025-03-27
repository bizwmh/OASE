/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Dataspace Gateway
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds.bundle;

import biz.car.config.BCS;

/**
 * Defines the messages of the Application Data Space.
 *
 * @version 1.0.0 02.03.2025 17:24:47
 */
public class MSG {

	public static String DS_NOT_REGISTERED;
	public static String DUPLICATE_KEY;
	public static String NOT_FOUND;

	// -------------------------------------------------------------------------
	// Initialize the static fields
	// -------------------------------------------------------------------------
	static {
		BCS.initialize(MSG.class, "MSG.properties"); //$NON-NLS-1$
	}

	private MSG() {
		super();
	}
}
