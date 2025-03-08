/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.merge.sort;

import java.io.File;
import java.io.IOException;
import java.util.function.Consumer;

import biz.car.csv.CSVRecord;
import biz.car.csv.CSVWriter;
import biz.oase.car.OASE;
import biz.oase.sm.context.ProcedureContext;

/**
 * The output file of the sort file worker.
 *
 * @version 1.0.0 08.03.2025 15:08:05
 */
public class SWOutput
	extends CSVWriter
	implements Consumer<CSVRecord> {

	private File myOutput;
	private ProcedureContext ctx;

	/**
	 * Creates a <code>SWOutput</code> instance.
	 * 
	 * @param aContext the associated procedure context
	 */
	public SWOutput(ProcedureContext aContext) {
		super();

		ctx = aContext;
	}

	@Override
	public void accept(CSVRecord aRecord) {
		try {
			write(aRecord);
		}
		catch (IOException anEx) {
			throw ctx.exception(anEx);
		}
	}

	/**
	 * @return the reference to the sort work output file.
	 */
	public File getFile() {
		return myOutput;
	}

	public void open() throws IOException {
		myOutput = generateFile();

		open(myOutput);
	}

	/**
	 * Creates a new CSV file with a generated temporary file name.
	 * 
	 * @return the generated file
	 * @throws IOException if the creation of the file fails
	 */
	File generateFile() {
		try {
			String l_prefix = ctx.getName() + "_"; //$NON-NLS-1$
			String l_suffix = ".csv"; //$NON-NLS-1$
			File l_outdir = new File(OASE.WORKSPACE);
			File l_ret = File.createTempFile(l_prefix, l_suffix, l_outdir);

			return l_ret;
		}
		catch (Exception anEx) {
			throw ctx.exception(anEx);
		}
	}
}
