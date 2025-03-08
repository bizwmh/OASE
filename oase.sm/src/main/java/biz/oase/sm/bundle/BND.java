/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.bundle;

import biz.car.config.BCS;

/**
 * Bundle Constants.
 *
 * @version 1.0.0 08.03.2025 14:33:02
 */
public class BND {

	public static int SORT_WORK_LIMIT;

	// -------------------------------------------------------------------------
	// Initialize the static fields
	// -------------------------------------------------------------------------
	static {
		BCS.initialize(BND.class, "BND.conf"); //$NON-NLS-1$
	}
}
