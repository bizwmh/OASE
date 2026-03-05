/* --------------------------------------------------------------------------
 * Project: CAR HTTP Client
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.http;

import biz.car.config.ACS;

/**
 * Defines symbolic constants for HTTP headers.
 *
 * @version 2.0.0 04.03.2026 08:50:51
 */
public class HEADER {

	public static String AUTHORIZATION;
	public static String CONTENT_TYPE;
	public static String LOCATION;

	// -------------------------------------------------------------------------
	// Initialize the static fields
	// -------------------------------------------------------------------------
	static {
		ACS.initialize(HEADER.class, "HDR"); //$NON-NLS-1$
	}

	/**
	 * Creates a default <code>MSG</code> instance.
	 */
	private HEADER() {
		super();
	}
}
