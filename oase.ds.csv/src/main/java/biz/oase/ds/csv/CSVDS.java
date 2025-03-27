/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE CSV Dataspace
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds.csv;

import biz.oase.ds.DSTable;
import biz.oase.ds.Dataspace;
import biz.oase.ds.csv.bundle.BND;

/**
 * Implements the <code>Dataspace</code> interface for CSV files.
 *
 * @version 1.0.0 14.03.2025 11:49:22
 */
public class CSVDS implements Dataspace {

	/**
	 * Creates a default <code>CSVDS</code> instance.
	 */
	public CSVDS() {
		super();
	}

	@Override
	public String getName() {
		return BND.DS_NAME;
	}

	@Override
	public DSTable getTable(String aName) {
		return new CSVTable(aName);
	}
}
