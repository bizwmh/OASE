/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Dataspace Interface
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds.bundle;

import biz.car.config.ACS;

/**
 * Defines the message constants.
 *
 * @version 2.0.0 19.10.2025 13:18:07
 */
public class MSG {


	public static String DS_NOT_FOUND;
	public static String DUPLICATE_KEY;
	public static String NOT_FOUND;

	// -------------------------------------------------------------------------
	// Initialize the static fields
	// -------------------------------------------------------------------------
	static {
		ACS.initialize(MSG.class, "MSG.properties"); //$NON-NLS-1$
	}
}
