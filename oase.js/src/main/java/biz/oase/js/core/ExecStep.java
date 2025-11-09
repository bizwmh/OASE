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
 * Runs an executable class instance.
 *
 * @version 2.0.0 08.11.2025 14:54:51
 */
public class ExecStep extends JobStep {

	/**
	 * Creates a default <code>ExecStep</code> instance.
	 * 
	 * @param aJob the current job
	 * @param anId the key of an entry in the underlying step configuration
	 */
	public ExecStep(Job aJob, String anId) {
		super(aJob, anId);
	}

	@Override
	public void exec() {
		Class<?> l_class = ClassUtil.Registry.get(stepId);
		
		if (l_class != null) {
			XRunnable l_exec = ClassUtil.newInstance(l_class);

			l_exec.accept(config());
			l_exec.run();
			
			return;
		}
		throw exception(MSG.EXEC_NOT_FOUND, stepId);
	}
}
