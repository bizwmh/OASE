/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.core;

import java.io.File;
import java.io.IOException;

import biz.car.XRuntimeException;
import biz.car.csv.CSVHeader;
import biz.car.csv.CSVReader;
import biz.car.csv.CSVRecord;
import biz.oase.sm.SMInput;
import biz.oase.sm.bundle.MSG;

/**
 * Provides read access to the application data objects.
 *
 * @version 2.0.0 19.10.2025 13:41:31
 */
public class Input extends Channel implements SMInput {

	private CSVRecord current;
	private CSVReader rdr;

	/**
	 * Creates a <code>Input</code> instance with the given name.
	 * 
	 * @param aName the name for this input channel
	 */
	public Input(String aName) {
		super(aName);

		rdr = new CSVReader();
	}

	@Override
	public void dispose() {
		rdr = null;
		current = null;

		super.dispose();
	}

	/**
	 * @return the current CSV record.<br>
	 *         For input channels this is the last read record.<br>
	 *         For output channels this is the next container to be written.
	 */
	@Override
	public CSVRecord getCurrent() {
		return current;
	}

	/**
	 * @return the header of this input channel
	 */
	public CSVHeader getHeader() {
		return rdr.header();
	}

	@Override
	public int getRecordCount() {
		return rdr.getRecordCount();
	}

	/**
	 * Reads the next application data object from the input channel.
	 * 
	 * @return the CSV record holding the application data object or
	 *         <code>null</code> if end of input has been reached
	 * @throws XRuntimeException if the read operation fails
	 */
	public CSVRecord readNext() {
		try {
			current = rdr.readRecord();

			return current;
		} catch (Throwable anEx) {
			int l_count = getRecordCount() + 1;

			error(MSG.READ_ERROR, getName(), l_count);
			throw exception(anEx);
		}
	}

	@Override
	protected void doClose() throws IOException {
		if (rdr != null) {
			rdr.close();
		}
	}

	@Override
	protected void doOpen() throws IOException {
		String l_path = getString(PATH);
		File l_file = new File(l_path);
		current = null;

		if (l_file.exists()) {
			rdr.open(l_file);
		} else {
			throw exception(MSG.FILE_NOT_FOUND, l_path, getClass().getSimpleName(), getName());
		}
	}
}
