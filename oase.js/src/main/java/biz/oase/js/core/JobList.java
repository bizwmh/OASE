/* --------------------------------------------------------------------------
 * Project: OASE Job Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.js.core;

import java.util.List;

/**
 * Processes the steps of a job stream.<br>
 * Steps are executed one after the other.
 *
 * @version 2.0.0 16.03.2026 19:13:58
 */
public class JobList extends JobSet {

	/**
	 * Creates a default <code>JobSet</code> instance.
	 * 
	 * @param aJob the current job
	 * @param anId the key of an entry in the underlying step configuration
	 */
	public JobList(Job aJob, String anId) {
		super(aJob, anId);
	}

	@Override
	protected void execute(List<JobStep> aList) {
		aList.forEach(step -> step.exec());
	}
}
