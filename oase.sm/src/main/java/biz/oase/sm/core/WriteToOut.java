/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.core;

import biz.car.csv.CSVRecord;
import biz.oase.sm.SM;

/**
 * This consumer writes the current CSV record from the input channel to the
 * associated output channel.
 *
 * @version 1.0.0 08.03.2025 14:37:25
 *
 */
public interface WriteToOut {

	static public void accept(Input anInput) {
		if (anInput.hasPath(SM.OUTPUT)) {
			String l_name = anInput.getString(SM.OUTPUT);
			Output l_out = anInput.context().getOutput(l_name);
			CSVRecord l_rec = anInput.getCurrent();

			l_out.write(l_rec);
		}
	}
}