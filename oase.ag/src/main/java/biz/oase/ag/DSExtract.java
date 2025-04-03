/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Application Gateway
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ag;

import java.io.IOException;
import java.util.List;

import com.typesafe.config.Config;

import biz.car.XRunnable;
import biz.car.config.ConfigObject;
import biz.car.csv.CSVHeader;
import biz.car.csv.CSVRecord;
import biz.car.csv.CSVWriter;
import biz.car.io.DataRecord;
import biz.oase.ds.DS;
import biz.oase.ds.DSCursor;
import biz.oase.ds.DSTable;
import biz.oase.ds.Dataspace;

/**
 * Runs a query against a data space.
 *
 * @version 1.0.0 04.03.2025 17:01:42
 */
public class DSExtract
		extends ConfigObject
		implements DS, XRunnable {

	private DSCursor dsc;
	private DSTable dst;
	private CSVWriter wrt;

	/**
	 * Creates a default <code>DSExtract</code> instance.
	 */
	public DSExtract() {
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
				wrt = null;

				throw exception(anEx);
			}
		}
	}

	@Override
	public void exec() {
		open();

		while (readOK()) {
			saveRecord();
		}
	}

	private void open() {
		try {
			// Load Table
			String l_id = getString(INPUT);
			Config l_conf = config();
			dst = Dataspace.getTable(l_id, l_conf).get();
			
			dst.accept(l_conf);
			
			// Load Cursor
			dsc = dst.query();
			
			// Open CSV Writer
			String l_path = getString(OUTPUT);
			wrt = new CSVWriter();

			wrt.open(l_path);
		} catch (Exception anEx) {
			throw exception(anEx);
		}
	}

	private boolean readOK() {
		return dsc.next();
	}

	private void saveRecord() {
		try {
			// convert data record to CSV
			DataRecord l_rec = dsc.get();
			List<String> l_fieldNames = l_rec.fieldNames();
			CSVHeader l_hdr = CSVRecord.Header(l_fieldNames);
			CSVRecord l_csv = l_hdr.Record();
			
			for (String l_field : l_fieldNames) {
				l_csv = l_csv.setValue(l_field, l_rec.getValue(l_field));
			}
			// write CSV to file system
			wrt.write(l_csv);
		} catch (IOException anEx) {
			throw exception(anEx);
		}
	}
}
