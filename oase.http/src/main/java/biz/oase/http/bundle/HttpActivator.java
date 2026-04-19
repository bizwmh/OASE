/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 * 			CAR HTTP Client
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.http.bundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import biz.car.util.ClassUtil;
import biz.oase.http.core.QueryJob;

/**
 * Registers the QueryJob class for the Job Service.
 *
 * @version 1.0.0 30.03.2026 19:59:04
 */
public class HttpActivator implements BundleActivator {

	/**
	 * Creates a default <code>HttpActivator</code> instance.
	 */
	public HttpActivator() {
		super();
	}

	@Override
	public void start(BundleContext context) throws Exception {
		ClassUtil.Registry.register("httpQuery", QueryJob.class); //$NON-NLS-1$
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// nothing to do
	}

}
