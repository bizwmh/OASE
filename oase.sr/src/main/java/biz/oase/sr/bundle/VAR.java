/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE System Reconciliation
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sr.bundle;

import biz.car.config.ACS;

/**
 * Bundle variables.
 *
 * @version 2.0.0 28.10.2025 08:56:57
 */
public class VAR {

	public static String ACTION;
	public static String BLANK;
	public static String DELETE;
	public static String INSERT;
	public static String NEW;
	public static String OLD;

	// -------------------------------------------------------------------------
	// Initialize the static fields
	// -------------------------------------------------------------------------
	static {
		ACS.initialize(VAR.class, "VAR.conf"); //$NON-NLS-1$
	}
}
