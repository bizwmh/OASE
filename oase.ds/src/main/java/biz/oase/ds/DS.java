/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Dataspace Interface
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds;

import biz.oase.framework.OASE;

/**
 * Symbolic constants for an application data space.
 *
 * @version 2.0.0 24.11.2025 11:06:17
 */
public interface DS extends OASE {

	/**
	 * The property key for a CSV delimiter.
	 */
	String DELIM = "delimiter"; //$NON-NLS-1$
	/**
	 * The property key for a dataspace.
	 */
	String DSID = "ds"; //$NON-NLS-1$
	/**
	 * Indicates that the insert operation for an entry failed due to an already
	 * existing entry with the same key.
	 */
	int DUPLICATE_KEY = 2;
	/**
	 * The property key for the export module.
	 */
	String EXPORT = "export"; //$NON-NLS-1$
	/**
	 * Indicates a target system operation that has failed.
	 */
	String KO = "KO"; //$NON-NLS-1$
	/**
	 * Indicates that the requested entry could not be found for a given key.
	 */
	int NOT_FOUND = 1;
	/**
	 * Indicates a successfully completed target system operation.
	 */
	String OK = "OK"; //$NON-NLS-1$
}
