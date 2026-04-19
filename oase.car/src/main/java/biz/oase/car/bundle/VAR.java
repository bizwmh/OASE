/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Common Application Runtime
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.car.bundle;

import biz.car.config.ACS;

/**
 * Bundle variables.
 *
 * @version 2.0.0 16.03.2026 09:06:55
 */
public class VAR {

	public static String GROUP;

	// -------------------------------------------------------------------------
	// Initialize the static fields
	// -------------------------------------------------------------------------
	static {
		ACS.initialize(VAR.class, "VAR.conf"); //$NON-NLS-1$
	}
}
