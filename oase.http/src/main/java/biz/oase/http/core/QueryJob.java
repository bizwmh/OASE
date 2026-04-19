/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 * 			CAR HTTP Client
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.http.core;

import biz.car.XRunnable;
import biz.car.config.CConfig;
import biz.car.util.Delay;

/**
 * Runs a query against a REST end point.
 *
 * @version 2.0.0 30.03.2026 19:53:17
 */
public class QueryJob extends CConfig implements XRunnable {

	/**
	 * Creates a default <code>QueryJob</code> instance.
	 */
	public QueryJob() {
		super();
	}

	@Override
	public void dispose() {
		// nothing to do
	}

	@Override
	public void exec() {
		Delay.seconds(3);
	}
}
