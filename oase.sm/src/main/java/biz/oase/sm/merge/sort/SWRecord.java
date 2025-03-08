/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.merge.sort;

import java.util.List;
import java.util.function.Consumer;

import biz.car.csv.CSVRecord;
import biz.oase.sm.context.ProcedureContext;

/**
 * Sort worker for CSV reccords.
 *
 * @version 1.0.0 08.03.2025 15:07:46
 */
public class SWRecord extends SortWorker<CSVRecord, List<CSVRecord>> {

	private SWList listConsumer;

	/**
	 * Creates a default <code>SWRecord</code> instance.
	 */
	public SWRecord(ProcedureContext aContext) {
		super(aContext);

		listConsumer = new SWList(aContext);
	}

	@Override
	public void dispose() {
		if (listConsumer != null) {
			listConsumer.dispose();
			super.dispose();

			listConsumer = null;
		}
	}

	@Override
	public void exit() {
		super.exit();
		listConsumer.exit();
	}

	@Override
	protected List<CSVRecord> buildResult() {
		List<CSVRecord> l_ret = List.copyOf(workList);

		return l_ret;
	}

	@Override
	protected Consumer<List<CSVRecord>> getResultConsumer() {
		return listConsumer;
	}
}
