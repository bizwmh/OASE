/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.bundle;

import biz.car.config.ACS;

/**
 * Bundle variables.
 *
 * @version 2.0.0 19.10.2025 13:55:38
 */
public class VAR {

	public static String DESCENDING;
	public static String FORMAT;
	public static String GROUP;
	public static String HEADER;
	public static String HEADER_REF;
	public static String HIGH_VALUE;
	public static String LOW_VALUE;
	public static String MERGE;
	public static String ON_EXIT;
	public static String ON_INIT;
	public static String ON_SELECTED;
	public static String SORT;
	public static String TYPE;
	public static String UNIQUE_KEY;

	// -------------------------------------------------------------------------
	// Initialize the static fields
	// -------------------------------------------------------------------------
	static {
		ACS.initialize(VAR.class, "VAR.conf"); //$NON-NLS-1$
	}
}
