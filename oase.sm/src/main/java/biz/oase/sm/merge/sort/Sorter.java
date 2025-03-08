/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.merge.sort;

import com.typesafe.config.Config;

import biz.car.csv.CSVRecord;
import biz.oase.sm.SM;
import biz.oase.sm.core.Input;
import biz.oase.sm.core.Procedure;

/**
 * Implementation of the sort procedure.
 *
 * @version 1.0.0 08.03.2025 15:10:43
 */
public class Sorter extends Procedure {

	private SWRecord recordWorker;
	private Input sortIn;

	/**
	 * Creates a default <code>Sorter</code> instance.
	 */
	public Sorter() {
		super();
	}

	@Override
	public void accept(Config aConfig) {
		super.accept(aConfig);

		recordWorker = new SWRecord(context());
		
		openInput();
	}

	@Override
	public void dispose() {
		if (recordWorker != null) {
			recordWorker.dispose();

			recordWorker = null;
		}
		closeInput();
		super.dispose();

		sortIn = null;
	}

	@Override
	protected void doMain() {
		CSVRecord l_rec = sortIn.getCurrent();

		recordWorker.accept(l_rec);
		sortIn.readNext();
	}

	@Override
	protected void exit() {
		recordWorker.exit();
	}

	@Override
	protected boolean hasInput() {
		CSVRecord l_rec = sortIn.getCurrent();

		return l_rec != null;
	}

	@Override
	protected void init() {
		// Get SORTIN and load first record
		sortIn = context().getInput(SM.INPUT);

		sortIn.readNext();
	}
}
