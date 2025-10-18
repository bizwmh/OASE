/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE CSV Dataspace
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds.csv;

import java.io.IOException;

import biz.car.config.ConfigAdapter;
import biz.car.csv.CSVRecord;
import biz.car.csv.CSVWriter;
import biz.car.io.DataRecord;
import biz.oase.ds.DSAPI;
import biz.oase.ds.DSResult;

/**
 * Stores the fields of a data space record in a CSV file.
 *
 * @version 1.0.0 25.03.2025 12:42:53
 */
public class CSVAPI extends ConfigAdapter implements DSAPI {

	private CSVWriter wrt;
	
	/**
	 * Creates a default <code>CSVAPI</code> instance.
	 */
	public CSVAPI() {
		super();
	}

	@Override
	public void close() {
		if (wrt != null) {
			try {
				wrt.close();

				wrt = null;
			} catch (IOException anEx) {
				warn(anEx.getMessage());
			}
		}
	}

	@Override
	public DSResult delete(DataRecord aRecord) {
		throw new UnsupportedOperationException();
	}

	@Override
	public DSResult insert(DataRecord aRecord) {
		CSVRecord l_rec = (CSVRecord) aRecord;
		DSResult l_ret = new DSResult();
		
		try {
			l_ret.accept(aRecord);
			wrt.write(l_rec);

			return l_ret;
		} catch (IOException anEx) {
			throw exception(anEx);
		}
	}

	@Override
	public void open() {
		try {
			CSVWriter l_wrt = new CSVWriter();
			String l_path = getString(PATH);
			
			l_wrt.open(l_path);
			
			wrt = l_wrt;
		} catch (IOException anEx) {
			throw exception(anEx);
		}
	}

	@Override
	public DSResult read(DataRecord aRecord) {
		throw new UnsupportedOperationException();
	}

	@Override
	public DSResult update(DataRecord aRecord) {
		throw new UnsupportedOperationException();
	}
}
