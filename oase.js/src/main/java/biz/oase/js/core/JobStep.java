/* --------------------------------------------------------------------------
 * Project: OASE Job Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.js.core;

import org.slf4j.Logger;

import com.typesafe.config.Config;

import biz.car.XLoggerFactory;
import biz.car.XRunnable;
import biz.car.config.CConfig;
import biz.car.util.LoggerDTO;

/**
 * Base class for any executable job configuration.
 *
 * @version 2.0.0 18.03.2026 16:19:53
 */
public abstract class JobStep
	extends CConfig
	implements XRunnable {

	public final String stepId;
	public final Job theJob;

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
	public void dispose() {
		// nothing to do
	}

	@Override
	public void run() {
		exec();
	}

	@Override
	protected Logger loggerFromConfig() {
		if (hasPath("jobLogger")) { //$NON-NLS-1$
			Config l_conf = config().getConfig("jobLogger"); //$NON-NLS-1$
			LoggerDTO l_dto = new LoggerDTO(l_conf);

			return XLoggerFactory.getLogger(l_dto).logger();
		}
		return super.loggerFromConfig();
	}
}
