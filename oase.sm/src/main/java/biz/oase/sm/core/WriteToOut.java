/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.core;

import biz.car.csv.CSVRecord;
import biz.oase.framework.OASE;
import biz.oase.sm.SMInput;
import biz.oase.sm.SMOutput;

/**
 * This consumer writes the current CSV record from the input channel to the
 * associated output channel.
 *
 * @version 2.0.0 19.10.2025 14:12:33
 *
 */
public interface WriteToOut extends OASE {

	static public void accept(SMInput anInput) {
		if (anInput.hasPath(OUTPUT)) {
			String l_name = anInput.getString(OUTPUT);
			SMOutput l_out = anInput.context().getOutput(l_name);
			CSVRecord l_rec = anInput.getCurrent();

			l_out.write(l_rec);
		}
	}
}