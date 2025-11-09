/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Job Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.js.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.Config;

import biz.car.XRunnable;
import biz.car.config.ConfigAdapter;

/**
 * Base class for any executable job configuration.
 *
 * @version 2.0.0 06.11.2025 08:51:05
 */
public abstract class JobStep
		extends ConfigAdapter
		implements XRunnable {

	public final String stepId;
	public final Job theJob;

	private Logger logger;

	/**
	 * Creates a default <code>JobStep</code> instance.
	 * 
	 * @param aJob the current job
	 * @param anId the key of an entry in the underlying step configuration
	 */
	public JobStep(Job aJob, String anId) {
		super();

		stepId = anId;
		theJob = aJob;
	}

	@Override
	public void accept(Config aConfig) {
		super.accept(aConfig);
		logger = theJob.logger();

		if (aConfig.hasPath(LOGGER)) {
			String l_logger = aConfig.getString(LOGGER);
			logger = LoggerFactory.getLogger(l_logger);
		}
	}

	@Override
	public void dispose() {
		// nothing to do
	}

	@Override
	public Logger logger() {
		return logger;
	}
}
