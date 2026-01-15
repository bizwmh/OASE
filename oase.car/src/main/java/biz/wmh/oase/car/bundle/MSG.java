/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Common Application Runtime
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved.
 * -------------------------------------------------------------------------- */

package biz.wmh.oase.car.bundle;

import biz.wmh.car.config.ACS;

/**
 * Framework messages.
 *
 * @version 2.0.0 17.10.2025 16:01:47
 */
public class MSG {

	public static String CLASS_NOT_FROM_BUNDLE;
	public static String COMPONENT_ACTIVATED;
	public static String COMPONENT_ACTIVATING;
	public static String COMPONENT_ACTIVATION_FAILED;
	public static String COMPONENT_DEACTIVATED;
	public static String COMPONENT_DEACTIVATING;

	// -------------------------------------------------------------------------
	// Initialize the static fields
	// -------------------------------------------------------------------------
	static {
		ACS.initialize(MSG.class, "MSG.properties"); //$NON-NLS-1$
	}

	/**
	 * Creates a default <code>MSG</code> instance.
	 */
	private MSG() {
		super();
	}
}