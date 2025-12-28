/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Dataspace Interface
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds.core;

import java.io.IOException;
import java.util.Iterator;

import biz.car.csv.CSVRecord;
import biz.car.csv.CSVWriter;

/**
 * Creates an export file of data entries in a data space.
 *
 * @version 2.0.0 24.11.2025 13:45:41
 */
public class Export extends DSClient {

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
		super.dispose();
	}

	@Override
	public void exec() {
		try {
			String l_out = getString(OUTPUT);
			wrt = new CSVWriter();

			wrt.open(l_out);

			Iterator<CSVRecord> l_iter = myAgent.query();

			while (l_iter.hasNext()) {
				CSVRecord l_rec = l_iter.next();
				l_rec = myMapper.apply(l_rec);
				
				wrt.write(l_rec);
			}
		} catch (IOException anEx) {
			throw exception(anEx);
		}
	}
}
