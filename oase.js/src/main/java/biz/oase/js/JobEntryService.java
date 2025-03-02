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
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import biz.car.io.FSObject;
import biz.oase.js.core.ExecJob;

/**
 * Service interface for submitting a file from the EXECLIB for execution.
 *
 * @version 1.0.0 12.02.2025 15:13:32
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
	 * @param aFile the job configuration file
	 * @return the resulting thread object
	 */
	static CompletableFuture<Void> submit(File aFile) {
		Config l_conf = ConfigFactory.parseFile(aFile);
		String l_name = ConfigName.apply(aFile);
		ExecJob l_step = new ExecJob(l_name, l_conf);
		
		l_step.accept(l_conf);

		return l_step.start(l_name.toUpperCase());
	}
}
