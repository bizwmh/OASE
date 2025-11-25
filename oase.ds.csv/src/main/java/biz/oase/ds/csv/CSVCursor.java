/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE CSV Dataspace
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds.csv;

import java.io.IOException;
import java.util.Iterator;

import biz.car.config.ConfigAdapter;
import biz.car.csv.CSVReader;
import biz.car.csv.CSVRecord;
import biz.oase.ds.DS;

/**
 * Produces data records from a CSV file.
 *
 * @version 1.0.0 24.03.2025 12:54:04
 */
public class CSVCursor extends ConfigAdapter
		implements DS, Iterator<CSVRecord> {

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
	public boolean hasNext() {
		try {
			rec = rdr.readRecord();

			if (rec == null) {
				close();

				return false;
			}
			return true;
		} catch (IOException anEx) {
			throw exception(anEx);
		}
	}

	@Override
	public CSVRecord next() {
		if (rec != null) {
			return rec;
		}
		throw new IllegalStateException();
	}

	public void open() {
		try {
			CSVReader l_rdr = new CSVReader();
			String l_path = getString(PATH);

			if (hasPath(DELIM)) {
				String l_delim = getString(DELIM);

				l_rdr.setDelimiter(l_delim);
			}
			l_rdr.open(l_path);

			rdr = l_rdr;
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
}
