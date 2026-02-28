/* --------------------------------------------------------------------------
 * Project: OASE H2 Web Console
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.h2.bundle;

import biz.car.config.ACS;

/**
 * Bundle messages.
 *
 * @version 2.0.0 08.01.2026 11:00:50
 */
public class MSG {

	public static String CONSOLE_DISABLED;
	public static String CONSOLE_STOPPED;

	// -------------------------------------------------------------------------
	// Initialize the static fields
	// -------------------------------------------------------------------------
	static {
		ACS.initialize(MSG.class, "MSG.properties"); //$NON-NLS-1$
	}

	/**
	 * Creates a default <code>VAR</code> instance.
	 */
	private MSG() {
		super();
	}
}