/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Dataspace Gateway
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds.bundle;

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
public class DSActivator implements BundleActivator {

	/**
	 * Creates a default <code>TRActivator</code> instance.
	 */
	public DSActivator() {
		super();
	}

	@Override
	public void start(BundleContext aContext) throws Exception {
		ClassUtil.Registry.register(VAR.EXTRACT, biz.oase.ds.DSExtract.class);
		ClassUtil.Registry.register(VAR.LOAD, biz.oase.ds.DSExtract.class);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// nothing to do
	}
}
