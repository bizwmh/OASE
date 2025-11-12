/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Job Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.js.core;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Processes the steps of a job stream.
 *
 * @version 2.0.0 08.11.2025 14:54:26
 */
public abstract class JobSet extends JobEntry {

	/**
	 * Creates a default <code>JobSet</code> instance.
	 * 
	 * @param aJob the current job
	 * @param anId the key of an entry in the underlying step configuration
	 */
	public JobSet(Job aJob, String anId) {
		super(aJob, anId);
	}

	@Override
	public void exec() {
		List<String> l_execs = asStringList(stepId); // stepId is the key given in the constructor
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
}
