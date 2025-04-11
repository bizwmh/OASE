/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE CSV Dataspace
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds.csv;

import java.io.IOException;

import com.typesafe.config.Config;

import biz.car.config.ConfigObject;
import biz.car.csv.CSVReader;
import biz.car.csv.CSVRecord;
import biz.car.io.DataRecord;
import biz.oase.ds.DS;
import biz.oase.ds.DSCursor;

/**
 * Produces data records from a CSV file.
 *
 * @version 1.0.0 24.03.2025 12:54:04
 */
public class CSVCursor extends ConfigObject implements DSCursor {

	private CSVReader rdr;
	private CSVRecord rec;

	/**
	 * Creates a default <code>CSVCursor</code> instance.
	 * 
	 * @param aName the name of the CSV table
	 */
	public CSVCursor(String aName) {
		super(aName);
	}

	@Override
	public void accept(Config aConfig) {
		Config l_conf = aConfig.getConfig(getName());
		super.accept(l_conf);

		open();
	}

	@Override
	public void dispose() {
		close();
	}

	@Override
	public DataRecord get() {
		return rec;
	}

	@Override
	public boolean next() {
		try {
			rec = rdr.readRecord();

			if (rec != null) {
				return true;
			} else {
				close();
				return false;
			}
		} catch (IOException anEx) {
			throw exception(anEx);
		}
	}

	private void close() {
		if (rdr != null) {
			try {
				rdr.close();

				rdr = null;
			} catch (IOException anEx) {
				warn(anEx.getMessage());
			}
		}
	}

	private void open() {
		try {
			CSVReader l_rdr = new CSVReader();
			String l_path = getString(PATH);
			
			if (hasPath(DS.DELIM)) {
				String l_delim = getString(DS.DELIM);
				
				l_rdr.setDelimiter(l_delim);
			}
			l_rdr.open(l_path);
			
			rdr = l_rdr;
		} catch (IOException anEx) {
			throw exception(anEx);
		}
	}
}
