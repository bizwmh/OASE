/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.bundle;

import biz.car.config.ACS;

/**
 * Bundle Constants.
 *
 * @version 2.0.0 30.03.2026 11:22:00
 */
public class BND {

	public static int SORT_WORK_LIMIT;

	// -------------------------------------------------------------------------
	// Initialize the static fields
	// -------------------------------------------------------------------------
	static {
		ACS.initialize(BND.class, "BND.conf"); //$NON-NLS-1$
	}
}
