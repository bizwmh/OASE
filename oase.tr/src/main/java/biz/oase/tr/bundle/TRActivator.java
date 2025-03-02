/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Test Runner
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.tr.bundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import biz.car.util.ClassUtil;

/**
 * Activates the TR bundle.<br>
 * The <code>SCNExec</code> class is registered in the <code>ClassUtil</code>
 * registry.
 *
 * @version 1.0.0 18.02.2025 11:41:27
 */
public class TRActivator implements BundleActivator {

	/**
	 * Creates a default <code>TRActivator</code> instance.
	 */
	public TRActivator() {
		super();
	}

	@Override
	public void start(BundleContext aContext) throws Exception {
		Class<?> l_class = biz.car.util.Dummy.class;
		
		ClassUtil.Registry.register("scn", l_class);
		ClassUtil.Registry.register("scnn", l_class);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// nothing to do
	}
}
