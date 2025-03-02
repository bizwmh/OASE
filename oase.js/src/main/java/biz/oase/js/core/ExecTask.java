/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Job Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.js.core;

import biz.car.XRunnable;
import biz.car.util.ClassUtil;
import biz.oase.js.bundle.MSG;

/**
 * Executes a single job step.
 *
 * @version 1.0.0 12.02.2025 16:00:07
 */
public class ExecTask extends JobStep {

	/**
	 * Creates a default <code>ExecStep</code> instance.
	 * 
	 * @param aJob the job task to execute
	 * @param aFile the name of the underlying configuration file
	 */
	public ExecTask(Job aJob, String aFile) {
		super(aJob, aFile);
	}

	@Override
	public void exec() {
		String l_exec = getString(EXEC);
		Class<?> l_class = ClassUtil.Registry.get(l_exec);
		
		if (l_class != null) {
			XRunnable l_task = ClassUtil.newInstance(l_class);

			l_task.accept(config());
			l_task.run();
			
			return;
		}
		throw exception(MSG.EXEC_NOT_FOUND, l_exec);
	}
}
