/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import biz.car.XRuntimeException;
import biz.car.csv.CSVHeader;
import biz.car.csv.CSVRecord;
import biz.car.csv.CSVWriter;
import biz.oase.sm.bundle.MSG;

/**
 * Provides write access for application data objects.
 *
 * @version 2.0.0 19.10.2025 13:44:04
 */
public class Output extends Channel {

	private CSVRecord current;
	private CSVWriter wrt;

	/**
	 * Creates a <code>Output</code> instance with the given name.
	 * 
	 * @param aName the name for this output channel
	 */
	public Output(String aName) {
		super(aName);

		wrt = new CSVWriter();
	}

	@Override
	public void dispose() {
		current = null;
		wrt = null;

		super.dispose();
	}

	@Override
	public CSVRecord getCurrent() {
		if (current == null) {
			current = wrt.header().Record();
		}
		return current;
	}

	@Override
	public int getRecordCount() {
		return wrt.getRecordCount();
	}

	/**
	 * Writes the cached CSV record to the receiving output channel.
	 * 
	 * @throws XRuntimeException if the write operation fails
	 */
	public void write() {
		if (current != null) {
			CSVRecord l_rec = getCurrent();
			current = null;

			write(l_rec);
		}
	}

	/**
	 * Sends the CSV record to the receiving output channel.
	 * 
	 * @param aRecord the record to be written
	 * @throws XRuntimeException if the write operation fails
	 */
	public void write(CSVRecord aRecord) {
		try {
			wrt.write(aRecord);
		} catch (Exception anEx) {
			int l_count = getRecordCount() + 1;

			error(MSG.WRITE_ERROR, getName(), l_count);
			throw exception(anEx);
		}
	}

	@Override
	protected void doClose() throws IOException {
		if (wrt != null) {
			wrt.close();
		}
	}

	@Override
	protected void doOpen() throws IOException {
		String l_path = getString(PATH);
		File l_file = new File(l_path);
		current = null;

		wrt.open(l_file);
		writeHeader();
	}

	/**
	 * Writes the header line to the output file.<br>
	 * The header fields are taken from the HEADER property.<br>
	 * Header references (tokens starting with '*.') are resolved to the header
	 * fields of the references input channel.
	 */
	private void writeHeader() {
		if (hasPath(HEADER)) {
			List<String> l_hdr = new ArrayList<String>();

			asStringList(HEADER).forEach(field -> {
				if (field.startsWith(HEADER_REF)) {
					String l_name = field.substring(HEADER_REF.length());
					Input l_in = context().getInput(l_name);
					CSVHeader l_csvHdr = l_in.getHeader();

					l_hdr.addAll(l_csvHdr.columns());
				} else {
					l_hdr.add(field);
				}
			});
			try {
				wrt.writeHeader(l_hdr);
			} catch (IOException anEx) {
				int l_count = getRecordCount() + 1;

				error(MSG.WRITE_ERROR, getName(), l_count);
				throw exception(anEx);
			}
		}
	}
}
