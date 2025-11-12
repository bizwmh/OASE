/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Job Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.js.core;

import java.util.List;

import com.typesafe.config.Config;

import biz.car.util.ClassUtil;
import biz.oase.js.bundle.MSG;
import biz.oase.js.bundle.VAR;

/**
 * Transforms a configuration entry into a job step.
 *
 * @version 1.0.0 09.11.2025 12:02:28
 */
public abstract class JobEntry extends JobStep {

	/**
	 * Creates a default <code>JobEntry</code> instance.
	 * 
	 * @param aJob the current job instance
	 * @param anId the key of an entry in the underlying step configuration
	 */
	public JobEntry(Job aJob, String anId) {
		super(aJob, anId);
	}

	/**
	 * Transforms a configuration entry into a job step.
	 * 
	 * @param anId the name of the configuration entry
	 * @return the job step found
	 */
	protected JobStep toJobStep(String anId) {
		List<String> l_list = asStringList(anId);

		switch (l_list.size()) {
		case 0:
			return jobStep0(anId);

		case 1:
			return jobStep1(anId);

		default:
			return jobStepN(anId);
		}
	}

	private JobStep jobStep0(String anId) {
		if (hasPath(anId)) {
			throw exception(MSG.PARAMETERS_MISSING, anId, getName());
		}
		Config l_conf = theJob.load(anId);
		JobStep l_ret = new ExecFile(theJob, anId);

		l_ret.accept(l_conf);

		return l_ret;
	}

	private JobStep jobStep1(String anId) {
		String l_exec = getString(anId);

		if (hasPath(l_exec)) {
			return jobStepN(l_exec);
		}
		if (ClassUtil.Registry.contains(l_exec)) {
			JobStep l_ret = new ExecStep(theJob, l_exec);

			l_ret.accept(config());

			return l_ret;
		}
		return jobStep0(l_exec);
	}

	private JobStep jobStepN(String anId) {
		Config l_conf = config();
		List<String> l_groups = asStringList(VAR.GROUP);

		if (l_groups.contains(anId)) {
			JobStep l_ret = new JobGroup(theJob, anId);

			l_ret.accept(l_conf);

			return l_ret;
		}
		JobStep l_ret = new JobList(theJob, anId);

		l_ret.accept(l_conf);

		return l_ret;
	}
}