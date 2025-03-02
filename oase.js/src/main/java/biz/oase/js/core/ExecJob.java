/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Job Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.js.core;

import java.util.List;

import com.typesafe.config.Config;

/**
 * Executes the job based on a job configuration file from the exec library.
 *
 * @version 1.0.0 12.02.2025 15:21:40
 */
public class ExecJob extends JobStep {

	/**
	 * Creates a default <code>ExecJob</code> instance.
	 * 
	 * @param aFile   the name of the underlying configuration file
	 * @param aConfig the job configuration file
	 */
	public ExecJob(String aFile, Config aConfig) {
		super(aFile);
		
		theJob.accept(aConfig);
	}

	/**
	 * Creates a default <code>ExecJob</code> instance.
	 * 
	 * @param aJob  the current job
	 * @param aFile the name of the underlying configuration file
	 */
	public ExecJob(Job aJob, String aFile) {
		super(aJob, aFile);
	}

	@Override
	public void exec() {
		JobStep l_task = jobTask();

		l_task.accept(config());
		l_task.run();
	}

	/**
	 * Determines the next job task to be executed.
	 */
	private JobStep jobTask() {
		List<String> l_execs = asStringList(EXEC);
		JobStep l_ret;

		if (l_execs.size() == 1) {
			String l_exec = l_execs.get(0);

			if (config().hasPath(l_exec)) {
				l_ret = new ExecGroup(theJob, l_exec);
			} else {
				l_ret = new ExecTask(theJob, configId);
			}
		} else {
			l_ret = new ExecList(theJob);
		}
		return l_ret;
	}
}
