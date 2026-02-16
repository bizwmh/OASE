/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Common Application Runtime
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.car.bundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import biz.car.util.ClassUtil;
import biz.car.util.Dummy;
import biz.oase.car.OASE;

/**
 * Registers the <code>Dummy</code> class in the <code>ClassUtil</code> registry
 * with key 'DUMMY' and provides some framework properties.
 *
 * @version 2.0.0 01.02.2026 17:45:13
 */
public class Activator implements BundleActivator {

	public static String CONFIG_AREA;
	public static String DATA_AREA;

	/**
	 * Creates a default <code>Activator</code> instance.
	 */
	public Activator() {
		super();
	}

	@Override
	public void start(BundleContext aContext) throws Exception {

		CONFIG_AREA = aContext.getProperty(VAL.framework_configuration_area);
		DATA_AREA = aContext.getProperty(VAL.framework_data_area);

		ClassUtil.Registry.register(OASE.DUMMY, Dummy.class);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		// nothing to do
	}
}
