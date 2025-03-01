/* --------------------------------------------------------------------------
 * Project: OASE SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.bundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import biz.car.util.ClassUtil;

/**
 * Activates this bundle.
 *
 * @version 1.0.0 14.02.2025 13:26:51
 */
public class SMActivator implements BundleActivator {

	/**
	 * Creates a default <code>SMActivator</code> instance.
	 */
	public SMActivator() {
		super();
	}

	@Override
	public void start(BundleContext aContext) throws Exception {
		Class<?> l_class = biz.car.util.Dummy.class;
		
		ClassUtil.Registry.register("extract", l_class);
		ClassUtil.Registry.register("sort", l_class);
		ClassUtil.Registry.register("merge", l_class);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// nothing to do
	}
}
