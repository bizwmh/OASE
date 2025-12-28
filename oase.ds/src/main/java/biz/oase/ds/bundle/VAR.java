/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Dataspace Interface
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds.bundle;

import biz.car.config.ACS;

/**
 * Bundle variables.
 *
 * @version 2.0.0 29.11.2025 13:12:50
 */
public class VAR {

	public static String DELIM;
	public static String DSID;
	public static int DUPLICATE_KEY;
	public static String EXPORT;
	public static String KO;
	public static String MAPPING;
	public static int NOT_FOUND;
	public static String OK;

	// -------------------------------------------------------------------------
	// Initialize the static fields
	// -------------------------------------------------------------------------
	static {
		ACS.initialize(VAR.class, "VAR.conf"); //$NON-NLS-1$
	}
}
