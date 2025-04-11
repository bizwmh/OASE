/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Dataspace Gateway
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds;

import biz.car.CAR;
import biz.oase.ds.bundle.VAR;

/**
 * Symbolic constants for an application data space.
 *
 * @version 1.0.0 20.03.2025 10:26:54
 */
public interface DS extends CAR {

	/**
	 * The property key for a CSV delimiter.
	 */
	String DELIM = "delim"; //$NON-NLS-1$
	/**
	 * The property key for a CSV delimiter.
	 */
	String DSID = VAR.DSID;
	/**
	 * Indicates that the insert operation for an entry failed due to an already
	 * existing entry with the same key.
	 */
	int DUPLICATE_KEY = 2;

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
