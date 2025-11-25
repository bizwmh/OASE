/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Dataspace Interface
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds.core;

import java.io.IOException;
import java.util.Iterator;

import biz.car.XRunnable;
import biz.car.config.ConfigAdapter;
import biz.car.csv.CSVRecord;
import biz.car.csv.CSVWriter;
import biz.oase.ds.DS;
import biz.oase.ds.DSAgent;
import biz.oase.ds.Dataspace;

/**
 * Creates an export file of data entries in a data space.
 *
 * @version 2.0.0 24.11.2025 13:45:41
 */
public class Export extends ConfigAdapter
		implements DS, XRunnable {

	private DSAgent myAgent;
	private CSVWriter wrt;

	/**
	 * Creates a default <code>Export</code> instance.
	 */
	public Export() {
		super();
	}

	@Override
	public void dispose() {
		// Close CSV Writer
		if (wrt != null) {
			try {
				wrt.close();

				wrt = null;
			} catch (IOException anEx) {
				throw exception(anEx);
			}
		}
		// Dispose agent
		if (myAgent != null) {
			myAgent.dispose();

			myAgent = null;
		}
	}

	@Override
	public void exec() {
		try {
			String l_name = getString(DSID);
			Dataspace l_ds = Dataspace.Registry.get(l_name);
			l_name = getString(INPUT);
			myAgent = l_ds.getAgent(l_name);
			l_name = getString(OUTPUT);
			wrt = new CSVWriter();

			myAgent.accept(config());
			wrt.open(l_name);

			Iterator<CSVRecord> l_iter = myAgent.query();

			while (l_iter.hasNext()) {
				wrt.write(l_iter.next());
			}
		} catch (IOException anEx) {
			throw exception(anEx);
		}
	}
}
