/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE CSV Dataspace
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds.csv.bundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import biz.oase.ds.DSRegistry;
import biz.oase.ds.Dataspace;
import biz.oase.ds.csv.CSVDS;

/**
 * Activates the CSV dataspace bundle.<br>
 * The CSV dataspace is registered.
 *
 * @version 1.0.0 14.03.2025 11:46:50
 */
public class CSVActivator implements BundleActivator {

	/**
	 * Creates a default <code>CSVActivator</code> instance.
	 */
	public CSVActivator() {
		super();
	}

	@Override
	public void start(BundleContext aContext) throws Exception {
		Dataspace l_ds = new CSVDS();
		
		DSRegistry.register(l_ds);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		Dataspace l_ds = new CSVDS();
		
		DSRegistry.unregister(l_ds);
	}
}
