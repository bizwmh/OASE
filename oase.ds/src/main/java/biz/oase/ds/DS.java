/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Dataspace Interface
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds;

import biz.oase.ds.bundle.VAR;
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
	String DELIM = VAR.DELIM;
	/**
	 * The property key for a dataspace.
	 */
	String DSID = VAR.DSID;
	/**
	 * Indicates that the insert operation for an entry failed due to an already
	 * existing entry with the same key.
	 */
	int DUPLICATE_KEY = VAR.DUPLICATE_KEY;
	/**
	 * The property key for the export module.
	 */
	String EXPORT = VAR.EXPORT;
	/**
	 * Indicates a target system operation that has failed.
	 */
	String KO = VAR.KO;
	/**
	 * The property key for a CSV mapper definition.
	 */
	String MAPPING = VAR.MAPPING;
	/**
	 * Indicates that the requested entry could not be found for a given key.
	 */
	int NOT_FOUND = VAR.NOT_FOUND;
	/**
	 * Indicates a successfully completed target system operation.
	 */
	String OK = VAR.OK;
}
