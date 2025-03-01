/* --------------------------------------------------------------------------
 * Project: OASE - Open Application Service Engine Framework
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved.
 * -------------------------------------------------------------------------- */

package biz.oase.car.bundle;

import biz.car.config.BCS;

/**
 * Framework messages.
 *
 * @version 1.0.0 07.02.2025 15:07:45
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
		BCS.initialize(MSG.class, "MSG.properties"); //$NON-NLS-1$
	}

	/**
	 * Creates a default <code>MSG</code> instance.
	 */
	private MSG() {
		super();
	}
}