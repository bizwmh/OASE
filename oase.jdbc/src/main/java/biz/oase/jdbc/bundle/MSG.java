/* --------------------------------------------------------------------------
 * Project: OASE JDBC
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.jdbc.bundle;

import biz.car.config.ACS;

/**
 * Bundle messages.
 *
 * @version 2.0.0 01.03.2026 08:25:51
 */
public class MSG {


	public static String CONNECTION_NOT_CLOSED;
	public static String DSFACTORY_NOT_FOUND;
	public static String SQL_STATEMENT_EMPTY;

	// -------------------------------------------------------------------------
	// Initialize the static fields
	// -------------------------------------------------------------------------
	static {
		ACS.initialize(MSG.class, "MSG.properties"); //$NON-NLS-1$
	}

	/**
	 * Creates a default <code>VAR</code> instance.
	 */
	private MSG() {
		super();
	}
}