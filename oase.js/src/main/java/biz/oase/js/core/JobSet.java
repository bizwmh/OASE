/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Job Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.js.core;

import java.util.List;
import java.util.stream.Collectors;

import com.typesafe.config.Config;

/**
 * Processes the steps of a job stream.<br>
 * Steps are executed concurrently.
 *
 * @version 1.0.0 12.02.2025 17:43:27
 */
public abstract class JobSet extends JobStep {

	/**
	 * Creates a default <code>ExecGroup</code> instance.
	 * 
	 * @param aJob the current job
	 * @param anId the id for the underlying configuration entry of the job set
	 */
	public JobSet(Job aJob, String anId) {
		super(aJob, anId);
	}

	@Override
	public void exec() {
		List<String> l_execs = asStringList(configId);
		List<JobStep> l_steps = l_execs.stream()
				.map(this::toJobStep)
				.collect(Collectors.toList());

		execute(l_steps);
	}

	/**
	 * Executes the set of job steps
	 * 
	 * @param aList the set of job steps
	 */
	protected abstract void execute(List<JobStep> aList);

	protected JobStep toJobStep(String aName) {
		JobStep l_ret = null;

		if (hasPath(aName)) {
			l_ret = new ExecGroup(theJob, aName);

			l_ret.accept(config());
		} else {
			Config l_conf = theJob.load(aName);
			l_ret = new ExecJob(theJob, aName);

			l_ret.accept(l_conf);
		}
		return l_ret;
	}
}
