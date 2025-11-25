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
import biz.oase.ds.DSResult;

/**
 * Writes CSV records to a dataset.
 *
 * @version 2.0.0 24.11.2025 12:58:10
 */
public class CSVInsert extends ConfigAdapter {

	private CSVWriter wrt;

	/**
	 * Creates a default <code>CSVInsert</code> instance.
	 */
	public CSVInsert(String aName) {
		super(aName);
	}

	/**
	 * Closes the output file.
	 */
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

	/**
	 * Opens the output file.
	 */
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

	/**
	 * TODO write
	 * 
	 * @param aRecord
	 * @return
	 */
	public DSResult write(CSVRecord aRecord) {
		// TODO Auto-generated method stub
		return null;
	}
}
