/* --------------------------------------------------------------------------
 * Project: CAR HTTP Client
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.http.core;

import biz.car.util.SFI;

/**
 * Application wide constants.<br>
 * The value of a constant is the name of the field.
 *
 * @version 2.0.0 04.03.2026 11:54:03
 */
public class VAL {

	public static String Bearer;
	public static String DELETE;
	public static String formParameter;
	public static String GET;
	public static String header;
	public static String method;
	public static String PATCH;
	public static String POST;
	public static String queryParameter;
	public static String resource;
	public static String url;

	// -------------------------------------------------------------------------
	// Initialize the static fields
	// -------------------------------------------------------------------------
	static {
		SFI.initialize(VAL.class);
	}

	/**
	 * Creates a default VAR instance.
	 */
	private VAL() {
		super();
	}
}
