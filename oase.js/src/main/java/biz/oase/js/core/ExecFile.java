/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Job Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.js.core;

import java.util.ArrayList;
import java.util.List;

import biz.oase.js.bundle.MSG;

/**
 * Executes the job based on a configuration file from the exec library.
 *
 * @version 2.0.0 08.11.2025 14:54:09
 */
public class ExecFile extends JobEntry {

	/**
	 * Creates a default <code>ExecFile</code> instance.
	 * 
	 * @param aJob  the current job
	 * @param aName the name of the job configuration file
	 */
	public ExecFile(Job aJob, String aName) {
		super(aJob, aName);
	}

	@Override
	public void exec() {
		List<String> l_list = new ArrayList<String>();
		JobStep l_step = toJobStep(EXEC);

		checkForLoop(l_list, EXEC);
		l_step.accept(config());
		l_step.run();
	}

	private void checkForLoop(List<String> aList, String aKey) {
		if (!aList.contains(aKey)) {
			aList.add(aKey);
			asStringList(aKey).stream()
					.forEach(key -> {
						if (hasPath(aKey)) {
							checkForLoop(aList, key);
						}
					});
			return;
		}
		throw exception(MSG.CONFIG_CYCLE_FOUND, stepId, aKey);
	}
}
