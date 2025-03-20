/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE CSV Dataspace
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds.csv;

import biz.oase.ds.DSEntity;
import biz.oase.ds.Dataspace;
import biz.oase.ds.csv.bundle.BND;

/**
 * Implements the <code>Dataspace</code> interface for CSV files.
 *
 * @version 1.0.0 14.03.2025 11:49:22
 */
public class CSVDataspace implements Dataspace {

	/**
	 * Creates a default <code>CSVDataspace</code> instance.
	 */
	public CSVDataspace() {
		super();
	}

	@Override
	public String getName() {
		return BND.CSVDS_NAME;
	}

	@Override
	public DSEntity getEntity(String aName) {
		return new CSVEntity(aName);
	}
}
