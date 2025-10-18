/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Dataspace Gateway
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds;

import biz.car.XRunnable;
import biz.car.config.ConfigAdapter;
import biz.car.csv.CSVFeeder;
import biz.car.csv.CSVHandler;
import biz.car.csv.CSVRecord;

/**
 * Performs operations against a data space resource.
 *
 * @version 1.0.0 04.03.2025 17:08:01
 */
public class DSLoad
		extends ConfigAdapter
		implements CSVHandler, XRunnable {

	/**
	 * Creates a default <code>DSLoad</code> instance.
	 */
	public DSLoad() {
		super();
	}

	@Override
	public void dispose() {
		// nothing to do
	}

	@Override
	public void exec() {
		CSVFeeder l_feeder = new CSVFeeder(this);
		
		l_feeder.accept(config());
		l_feeder.exec();
	}

	@Override
	public void onInit() {
		// nothing to do
	}

	@Override
	public void onExit() {
		// nothing to do
	}

	@Override
	public void onError(Exception anEx) {
		throw exception(anEx);
	}

	@Override
	public void handle(CSVRecord aRecord) {
		// nothing to do
	}
}
