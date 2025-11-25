/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE CSV Dataspace
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds.csv.bundle;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

import biz.oase.ds.Dataspace;
import biz.oase.ds.csv.CSVDS;

/**
 * Activates the CSV dataspace bundle.<br>
 * The CSV dataspace is registered.
 *
 * @version 2.0.0 21.11.2025 11:17:10
 */
public class CSVActivator implements BundleActivator {

	private String dsid;
	
	/**
	 * Creates a default <code>CSVActivator</code> instance.
	 */
	public CSVActivator() {
		super();
	}

	@Override
	public void start(BundleContext aContext) throws Exception {
		Dataspace l_ds = new CSVDS();
		dsid = l_ds.getId();
		
		Dataspace.Registry.register(l_ds);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		Dataspace.Registry.unregister(dsid);
	}
}
