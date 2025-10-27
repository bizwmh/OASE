/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.bundle;

import biz.car.util.SFI;

/**
 * Bundle constants.
 *
 * @version 2.0.0 19.10.2025 14:08:01
 */
public class VAL {

	public static String date;
	public static String DUMMY;
	public static String integer;
	public static String SORTOUT;

	// -------------------------------------------------------------------------
	// Initialize the static fields
	// -------------------------------------------------------------------------
	static {
		SFI.initialize(VAL.class);
	}
}
