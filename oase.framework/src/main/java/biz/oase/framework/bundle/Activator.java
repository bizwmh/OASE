/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Framework
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.framework.bundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import biz.car.util.ClassUtil;
import biz.car.util.Dummy;

/**
 * Registers the <code>Dummy</code> class in the <code>ClassUtil</code>
 * registry with key 'DUMMY'.
 *
 * @version 2.0.0 10.11.2025 13:12:07
 */
public class Activator implements BundleActivator {

	public static String CONFIG_AREA;
	public static String WORKSPACE;
	
	/**
	 * Creates a default <code>Activator</code> instance.
	 */
	public Activator() {
		super();
	}

	@Override
	public void start(BundleContext aContext) throws Exception {

		register("DUMMY", Dummy.class); //$NON-NLS-1$
		CONFIG_AREA = aContext.getProperty("framework.configuration.area"); //$NON-NLS-1$
		WORKSPACE = aContext.getProperty("framework.workspace.area"); //$NON-NLS-1$
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
