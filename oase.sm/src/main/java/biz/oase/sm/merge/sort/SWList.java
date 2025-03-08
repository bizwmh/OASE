/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.merge.sort;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;

import biz.car.csv.CSVRecord;
import biz.oase.sm.bundle.MSG;
import biz.oase.sm.context.ProcedureContext;

/**
 * Sort worker for a list of CSV records.
 *
 * @version 1.0.0 08.03.2025 15:09:18
 */
public class SWList extends SortWorker<List<CSVRecord>, File> {

	private RecordComparator comp;
	private SWOutput csvOut;
	private SWFile fileConsumer;

	/**
	 * Creates a default <code>SWList</code> instance.
	 * 
	 * @param aContext the associated procedure context
	 */
	public SWList(ProcedureContext aContext) {
		super(aContext);

		comp = new RecordComparator(ctx);
		fileConsumer = new SWFile(ctx);
		csvOut = new SWOutput(ctx);
	}

	@Override
	public void dispose() {
		if (fileConsumer != null) {
			fileConsumer.dispose();

			fileConsumer = null;
		}
		if (comp != null) {
			comp.dispose();

			comp = null;
		}
		super.dispose();
	}

	@Override
	public void exit() {
		super.exit();
		fileConsumer.exit();
	}

	@Override
	protected File buildResult() {
		try {
			csvOut.open();
			workList.stream()
				.flatMap(list -> list.stream())
				.sorted(comp)
				.forEach(csvOut);
			csvOut.close();

			return csvOut.getFile();
		}
		catch (Exception anEx) {
			throw ctx.exception(MSG.SORTWORKER_ERROR, csvOut.getName(),
				anEx.getMessage());
		}
	}

	@Override
	protected Consumer<File> getResultConsumer() {
		return fileConsumer;
	}
}
