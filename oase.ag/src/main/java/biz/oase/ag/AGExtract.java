/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Test Runner
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ag;

import biz.car.XRunnable;
import biz.car.config.ConfigObject;

/**
 * Runs a query against a data space.
 *
 * @version 1.0.0 04.03.2025 17:01:42
 */
public class AGExtract
		extends ConfigObject
		implements XRunnable {

	/**
	 * Creates a default <code>AGExtract</code> instance.
	 */
	public AGExtract() {
		super();
	}

	@Override
	public void dispose() {
		// nothing to do
	}

	@Override
	public void exec() {
		// TODO Auto-generated method stub

	}
}
