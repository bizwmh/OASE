/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Job Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.js.bundle;

import biz.car.config.BCS;

/**
 * Messages of the ASGi Job Service.
 *
 * @version 1.0.0 11.02.2025 12:21:49
 */
public class MSG {

	public static String DUPLICATE_EXEC_MEMBER;
	public static String EXEC_MISSING;
	public static String EXEC_NOT_FOUND;
	public static String EXECGROUP_ABENDED;
	public static String FILE_NOT_FOUND;
	public static String JOB_CYCLE_FOUND;
	public static String JOB_SUBMITTED;

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
