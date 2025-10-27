/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.sort;

import java.util.List;

import com.typesafe.config.Config;

import biz.car.csv.CSVRecord;
import biz.oase.sm.bundle.MSG;
import biz.oase.sm.core.Input;
import biz.oase.sm.core.Procedure;

/**
 * Implementation of the sort procedure.
 *
 * @version 2.0.0 21.10.2025 15:55:13
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
		// validate input parameter
		List<String> l_list = context().inputNames();
		
		if (l_list.size() != 1) {
			throw exception(MSG.INVALID_SORT_INPUT, getName());
		}
		sortIn = context().getInput(l_list.get(0));

		sortIn.open();
		sortIn.readNext();
	}
}
