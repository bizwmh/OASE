/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE CSV Dataspace
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds.csv;

import java.util.Iterator;

import com.typesafe.config.Config;

import biz.car.config.ConfigAdapter;
import biz.car.csv.CSVRecord;
import biz.oase.ds.DSAgent;
import biz.oase.ds.DSResult;

/**
 * Performs operations on a CSV dataset.
 *
 * @version 2.0.0 24.11.2025 10:41:44
 */
public class CSVAgent extends ConfigAdapter implements DSAgent {

	private CSVInsert wrt;

	/**
	 * Creates a default <code>CSVAgent</code> instance.
	 * 
	 * @param aName the name of the CSV agent
	 */
	public CSVAgent(String aName) {
		super(aName);
	}

	@Override
	public void accept(Config aConfig) {
		Config l_conf = aConfig;
		String l_name = getName();

		if (aConfig.hasPath(l_name)) {
			l_conf = aConfig.getConfig(l_name);
		}
		super.accept(l_conf);
	}

	@Override
	public DSResult delete(CSVRecord aRecord) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void dispose() {
		if (wrt != null) {
			wrt.close();

			wrt = null;
		}
	}

	@Override
	public DSResult insert(CSVRecord aRecord) {
		CSVInsert l_wrt = getInsert();
		DSResult l_ret = l_wrt.write(aRecord);

		return l_ret;
	}

	@Override
	public Iterator<CSVRecord> query() {
		CSVCursor l_ret = new CSVCursor(getName());

		l_ret.accept(config());
		l_ret.open();

		return l_ret;
	}

	@Override
	public DSResult read(CSVRecord aRecord) {
		throw new UnsupportedOperationException();
	}

	@Override
	public DSResult update(CSVRecord aRecord) {
		throw new UnsupportedOperationException();
	}

	private CSVInsert getInsert() {
		if (wrt == null) {
			wrt = new CSVInsert(getName());

			wrt.accept(config());
			wrt.open();
		}
		return wrt;
	}
}
