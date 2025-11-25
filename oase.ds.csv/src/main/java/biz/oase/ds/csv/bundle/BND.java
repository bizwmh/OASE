/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE CSV Dataspace
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds.csv.bundle;

import biz.car.config.ACS;

/**
 * Bundle Constants.
 *
 * @version 2.0.0 21.11.2025 11:27:54
 */
public class BND {

	public static String DS_ID;

	// -------------------------------------------------------------------------
	// Initialize the static fields
	// -------------------------------------------------------------------------
	static {
		ACS.initialize(BND.class, "BND.conf"); //$NON-NLS-1$
	}
}
