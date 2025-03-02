/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Job Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.js.bundle;

import biz.car.config.BCS;

/**
 * Bundle variables.
 *
 * @version 1.0.0 11.02.2025 12:19:50
 */
public class VAR {

	public static String EXECLIB;
	public static String INBOX;
	public static String OUTBOX;

	// -------------------------------------------------------------------------
	// Initialize the static fields
	// -------------------------------------------------------------------------
	static {
		BCS.initialize(VAR.class, "VAR.conf"); //$NON-NLS-1$
	}
}
