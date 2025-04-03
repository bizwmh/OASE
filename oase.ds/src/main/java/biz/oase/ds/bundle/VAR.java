/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Dataspace Interface
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved.
 * -------------------------------------------------------------------------- */

package biz.oase.ds.bundle;

import biz.car.config.BCS;

/**
 * Bundle Constants.
 *
 * @version 1.0.0 07.02.2025 15:07:25
 */
public class VAR {

	public static String DSID;
	
	// -------------------------------------------------------------------------
	// Initialize the static fields
	// -------------------------------------------------------------------------
	static {
		BCS.initialize(VAR.class, "VAR.conf"); //$NON-NLS-1$
	}
}
