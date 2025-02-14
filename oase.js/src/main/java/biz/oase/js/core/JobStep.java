/* --------------------------------------------------------------------------
 * Project: OASE Job Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.js.core;

import org.slf4j.Logger;

import biz.car.XRunnable;
import biz.car.config.ConfigObject;
import biz.oase.framework.OASE;

/**
 * Base class for any executable job configuration.
 *
 * @version 1.0.0 12.02.2025 15:35:02
 */
public abstract class JobStep
		extends ConfigObject
		implements OASE,
		XRunnable {

	public final String configId;
	public final Job theJob;

	/**
	 * Creates a default <code>JobStep</code> instance.
	 * 
	 * @param aJob the current job
	 * @param anId the name of the underlying configuration id
	 */
	public JobStep(Job aJob, String anId) {
		super();

		configId = anId;
		theJob = aJob;
	}

	/**
	 * Creates a default <code>JobStep</code> instance.
	 * 
	 * @param anId the name of the underlying configuration id
	 */
	public JobStep(String anId) {
		super();

		configId = anId;
		theJob = new Job(anId);
	}

	@Override
	public void dispose() {
		// nothing to do
	}

	@Override
	public Logger logger() {
		return theJob.logger();
	}
}
