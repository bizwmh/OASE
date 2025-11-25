/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE System Reconciliation
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sr.bundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import biz.car.util.ClassUtil;
import biz.oase.sr.SRClient;

/**
 * Registers the system reconciliation procedure in the <code>ClassUtil</code>
 * registry.
 *
 * @version 2.0.0 31.10.2025 18:02:22
 */
public class SRActivator implements BundleActivator {

	/**
	 * Creates a default <code>SMActivator</code> instance.
	 */
	public SRActivator() {
		super();
	}

	@Override
	public void start(BundleContext context) throws Exception {
		
		register("OASE.SR", SRClient.class); //$NON-NLS-1$
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// nothing to do
	}

	private void register(String anId, Class<?> aClass) {
		if (ClassUtil.Registry.contains(anId)) {
			ClassUtil.Registry.unregister(anId);
		}
		ClassUtil.Registry.register(anId, aClass);
	}
}
