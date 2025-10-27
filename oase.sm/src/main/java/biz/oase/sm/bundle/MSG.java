/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.bundle;

import biz.car.config.ACS;

/**
 * Defines the message constants.
 *
 * @version 2.0.0 19.10.2025 13:18:07
 */
public class MSG {

	public static String CHANNEL_ClOSED;
	public static String CHANNEL_ENTRY_MISSING;
	public static String CHANNEL_OPENED;
	public static String CLASS_UNREGISTERED;
	public static String ClOSE_ERROR;
	public static String DUPLICATE_CHANNEL_ENTRY;
	public static String EXEC_INITIALIZED;
	public static String FILE_NOT_FOUND;
	public static String GROUP_LEVEL_MISMATCH;
	public static String GROUP_NOT_FOUND;
	public static String INVALID_DATATYPE;
	public static String INVALID_SORT_INPUT;
	public static String NO_INPUT_CHANNELS;
	public static String OPEN_ERROR;
	public static String READ_ERROR;
	public static String SORT_ERROR;
	public static String SORTWORKER_ERROR;
	public static String WRITE_ERROR;

	// -------------------------------------------------------------------------
	// Initialize the static fields
	// -------------------------------------------------------------------------
	static {
		ACS.initialize(MSG.class, "MSG.properties"); //$NON-NLS-1$
	}
}
