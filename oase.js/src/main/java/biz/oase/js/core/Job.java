/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Job Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.js.core;

import static biz.oase.js.bundle.CFG.EXECLIB;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import biz.car.config.ConfigAdapter;
import biz.oase.js.ExecLib;
import biz.oase.js.bundle.MSG;

/**
 * The singleton <code>Job</code> instance for running executable job
 * configuration files.
 *
 * @version 2.0.0 08.11.2025 14:33:42
 */
public class Job extends ConfigAdapter {

	private List<String> stepList;

	/**
	 * Creates a default <code>Job</code> instance.
	 */
	public Job() {
		super();

		stepList = new ArrayList<String>();
	}

	/**
	 * Validates and loads the configuration file for the next job step.
	 * 
	 * @param aMember the base name of the configuration file
	 * @return the step configuration
	 */
	public Config load(String aMember) {
		if (!stepList.contains(aMember)) {
			File l_file = ExecLib.get(aMember)
					.orElseThrow(() -> exception(MSG.FILE_NOT_FOUND, aMember, EXECLIB));
			Config l_ret = ConfigFactory.parseFile(l_file);

			if (l_ret.hasPath(EXEC)) {
				stepList.add(aMember);

				return l_ret;
			}
			throw exception(MSG.EXEC_MISSING, aMember);
		}
		throw exception(MSG.JOB_CYCLE_FOUND, aMember);
	}
}
