/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.bundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import biz.car.util.ClassUtil;
import biz.oase.sm.SM;
import biz.oase.sm.merge.Merger;
import biz.oase.sm.sort.Sorter;

/**
 * Registers the sort and merge procedures in the <code>ClassUtil</code>
 * registry.
 *
 * @version 2.0.0 30.03.2026 11:21:34
 */
public class SMActivator implements BundleActivator {

	/**
	 * Creates a default <code>SMActivator</code> instance.
	 */
	public SMActivator() {
		super();
	}

	@Override
	public void start(BundleContext context) throws Exception {

		register(SM.MERGE, Merger.class);
		register(SM.SORT, Sorter.class);
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
