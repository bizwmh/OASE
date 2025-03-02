/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Job Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.js.core;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.stream.Collectors;

import biz.oase.js.bundle.MSG;

/**
 * Processes the steps of a job stream.<br>
 * Steps are executed concurrently.
 *
 * @version 1.0.0 12.02.2025 16:01:05
 */
public class ExecGroup extends JobSet implements ThreadFactory {

	/**
	 * Creates a default <code>ExecGroup</code> instance.
	 * 
	 * @param aJob   the current job
	 * @param aGroup the name of the underlying configuration id for the group entry
	 */
	public ExecGroup(Job aJob, String aGroup) {
		super(aJob, aGroup);
	}

	@Override
	public Thread newThread(Runnable aWorker) {
		String l_name = theJob.getName() + "." + getName(); //$NON-NLS-1$
		Thread l_ret = new Thread(aWorker, l_name);

		l_ret.setDaemon(true);

		return l_ret;
	}

	@Override
	protected void execute(List<JobStep> aList) {
		Executor l_exec = Executors.newFixedThreadPool(aList.size(), this);
		List<CompletableFuture<Void>> l_threads = aList.stream()
				// start each job task as a thread
				.map(step -> step.start(l_exec))
				.collect(Collectors.toList());
		// and wait for the list of all started job tasks to complete
		List<Throwable> l_errors = l_threads.stream()
				// and wait for the list of all started job tasks to complete
				.map(step -> waitFor(step))
				// collect all exceptions
				.filter(ex -> ex != null)
				.collect(Collectors.toList());

		if (l_errors.size() > 0) {
			throw exception(MSG.EXECGROUP_ABENDED, configId);
		}
	}

	/**
	 * Waits for the completion of a <code>Future</code><br>
	 * Exceptions are caught, logged and do not terminate the scenario set.
	 * 
	 * @param aThread the <code>Future</code> to wait for
	 */
	private Throwable waitFor(Future<?> aThread) {
		Throwable l_ret = null;

		try {
			join(aThread);
		} catch (Throwable anEx) {
			l_ret = anEx;
		}
		return l_ret;
	}
}
