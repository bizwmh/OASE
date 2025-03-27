/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE CSV Dataspace
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds.csv.bundle;

import biz.car.config.BCS;

/**
 * Bundle Constants.
 *
 * @version 1.0.0 14.03.2025 12:29:05
 */
public class BND {

	public static String DS_NAME;

	// -------------------------------------------------------------------------
	// Initialize the static fields
	// -------------------------------------------------------------------------
	static {
		BCS.initialize(BND.class, "BND.conf"); //$NON-NLS-1$
	}
}
