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
 * @version 1.0.0 28.10.2024 09:57:12
 */
public class ExecList extends JobSet {

	/**
	 * Creates a default <code>ExecList</code> instance.
	 * 
	 * @param aJob the associated job
	 */
	public ExecList(Job aJob) {
		super(aJob, EXEC);
	}

	@Override
	protected void execute(List<JobStep> aList) {
		aList.forEach(step -> step.run());
	}
}
