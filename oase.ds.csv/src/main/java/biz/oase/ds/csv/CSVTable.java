/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE CSV Dataspace
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds.csv;

import biz.car.config.ConfigObject;
import biz.oase.ds.DSAPI;
import biz.oase.ds.DSCursor;
import biz.oase.ds.DSTable;

/**
 * A data space table for CSV records.
 *
 * @version 1.0.0 23.03.2025 12:47:15
 */
public class CSVTable extends ConfigObject implements DSTable {

	/**
	 * Creates a default <code>CSVTable</code> instance.
	 * @param aName the name of the table
	 */
	public CSVTable(String aName) {
		super();
	}

	@Override
	public DSAPI getAPI() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DSCursor query() {
		// TODO Auto-generated method stub
		return null;
	}
}
