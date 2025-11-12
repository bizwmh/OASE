/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Job Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.js.bundle;

import biz.car.config.ACS;

/**
 * Messages of the ASGi Job Service.
 *
 * @version 2.0.0 08.11.2025 14:30:38
 */
public class MSG {

	public static String CONFIG_CYCLE_FOUND;
	public static String DUPLICATE_LIB_MEMBER;
	public static String EXEC_MISSING;
	public static String EXEC_NOT_FOUND;
	public static String EXECGROUP_ABENDED;
	public static String FILE_NOT_FOUND;
	public static String JOB_CYCLE_FOUND;
	public static String JOB_SUBMITTED;
	public static String PARAMETERS_MISSING;

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
