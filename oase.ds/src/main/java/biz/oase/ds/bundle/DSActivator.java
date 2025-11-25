/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Dataspace Interface
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds.bundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import biz.car.util.ClassUtil;
import biz.oase.ds.DS;
import biz.oase.ds.core.Export;

/**
 * Registers the sort and merge procedures in the <code>ClassUtil</code>
 * registry.
 *
 * @version 2.0.0 24.10.2025 11:58:29
 */
public class DSActivator implements DS, BundleActivator {

	/**
	 * Creates a default <code>DSActivator</code> instance.
	 */
	public DSActivator() {
		super();
	}

	@Override
	public void start(BundleContext context) throws Exception {

		register(DS.EXPORT, Export.class);
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
