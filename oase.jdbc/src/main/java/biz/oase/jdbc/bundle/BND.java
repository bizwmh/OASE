/* --------------------------------------------------------------------------
 * Project: OASE JDBC
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.jdbc.bundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * Provides access to a reference of the bundle context.
 *
 * @version 2.0.0 01.03.2026 07:16:33
 */
public class BND implements BundleActivator {

	public static BundleContext context;

	/**
	 * Creates a default <code>BND</code> instance.
	 */
	public BND() {
		super();
	}

	@Override
	public void start(BundleContext aContext) throws Exception {
		context = aContext;
	}

	@Override
	public void stop(BundleContext aContext) throws Exception {
		context = null;
	}
}
