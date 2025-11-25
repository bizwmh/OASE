/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE CSV Dataspace
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds.csv;

import biz.oase.ds.DSAgent;
import biz.oase.ds.Dataspace;
import biz.oase.ds.csv.bundle.BND;

/**
 * Implements the <code>Dataspace</code> interface for CSV files.
 *
 * @version 2.0.0 21.11.2025 11:28:49
 */
public class CSVDS implements Dataspace {

	/**
	 * Creates a default <code>CSVDS</code> instance.
	 */
	public CSVDS() {
		super();
	}

	@Override
	public String getId() {
		return BND.DS_ID;
	}

	@Override
	public DSAgent getAgent(String aName) {
		return new CSVAgent(aName);
	}
}
