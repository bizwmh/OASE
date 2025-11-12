/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Job Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.js;

import static biz.car.io.PrefixedFile.isPrefixed;
import static biz.car.io.PrefixedFile.trimPrefix;

import java.io.File;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import biz.car.io.FSObject;
import biz.oase.js.core.ExecFile;
import biz.oase.js.core.Job;
import biz.oase.js.core.JobStep;

/**
 * Service interface for submitting a file from the EXECLIB for execution.
 *
 * @version 2.0.0 09.11.2025 13:45:32
 */
public interface JobEntryService {

	Function<File, String> ConfigName = aFile -> {
		FSObject l_fso = () -> aFile;
		String l_ret = l_fso.getBaseName();

		if (isPrefixed(l_ret)) {
			l_ret = trimPrefix(l_ret);
		}
		return l_ret;
	};

	/**
	 * Submits the given file for execution.
	 * 
	 * @param aName the name of the job configuration file
	 * @return the resulting thread object or <code>null</code> if no job file could
	 *         be found for the given name
	 */
	static CompletableFuture<Void> submit(String aName) {
		CompletableFuture<Void> l_ret = null;
		Optional<File> l_file = ExecLib.get(aName);

		if (l_file.isPresent()) {
			File l_jobFile = l_file.get();
			Config l_conf = ConfigFactory.parseFile(l_jobFile);
			Job l_job = new Job();

			l_job.accept(l_conf);

			JobStep l_step = new ExecFile(l_job, aName);

			l_step.accept(l_conf);

			l_ret = submit(l_jobFile);
		}
		return l_ret;
	}

	/**
	 * Submits the given file for execution.
	 * 
	 * @param aFile the job configuration file
	 * @return the resulting thread object or <code>null</code> if no job file could
	 *         be found for the given name
	 */
	static CompletableFuture<Void> submit(File aFile) {
		CompletableFuture<Void> l_ret = null;
		Config l_conf = ConfigFactory.parseFile(aFile);
		String l_name = ConfigName.apply(aFile);
		Job l_job = new Job();

		l_job.accept(l_conf);

		JobStep l_step = new ExecFile(l_job, l_name);

		l_step.accept(l_conf);

		l_ret = l_step.start(l_step.getName());
		
		return l_ret;
	}
}
