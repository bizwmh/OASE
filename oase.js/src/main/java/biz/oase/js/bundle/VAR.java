/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Job Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.js.bundle;

import biz.car.config.ACS;

/**
 * Bundle variables.
 *
 * @version 2.0.0 08.11.2025 14:31:10
 */
public class VAR {

	public static String EXECLIB;
	public static String GROUP;
	public static String INBOX;
	public static String OUTBOX;

	// -------------------------------------------------------------------------
	// Initialize the static fields
	// -------------------------------------------------------------------------
	static {
		ACS.initialize(VAR.class, "VAR.conf"); //$NON-NLS-1$
	}
}
