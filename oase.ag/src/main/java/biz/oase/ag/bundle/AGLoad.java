/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Test Runner
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ag.bundle;

import biz.car.XRunnable;
import biz.car.config.ConfigObject;
import biz.car.csv.CSVFeeder;
import biz.car.csv.CSVHandler;
import biz.car.csv.CSVRecord;

/**
 * Performs operations against a data space resource.
 *
 * @version 1.0.0 04.03.2025 17:08:01
 */
public class AGLoad
		extends ConfigObject
		implements CSVHandler, XRunnable {

	/**
	 * Creates a default <code>AGLoad</code> instance.
	 */
	public AGLoad() {
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
