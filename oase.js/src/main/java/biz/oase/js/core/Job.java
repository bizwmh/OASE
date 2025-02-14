/* --------------------------------------------------------------------------
 * Project: OASE Job Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.js.core;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import biz.car.config.ConfigObject;
import biz.oase.js.ExecLib;
import biz.oase.js.bundle.BND;
import biz.oase.js.bundle.MSG;
import biz.oase.js.bundle.VAR;

/**
 * The singleton <code>Job</code> instance for running an executable job
 * configuration file.
 *
 * @version 1.0.0 12.02.2025 15:16:00
 */
public class Job extends ConfigObject {

	private List<String> stepList;
	private List<String> execLibs;
	private Map<String, File> members;

	/**
	 * Creates a default <code>Job</code> instance.
	 */
	public Job() {
		super();

		stepList = new ArrayList<String>();
		execLibs = new ArrayList<String>();
	}

	@Override
	public void accept(Config aConfig) {
		super.accept(aConfig);

		List<String> l_list = asStringList(VAR.EXECLIB);
		members = ExecLib.fileMap(BND.EXECLIB);
		
		execLibs.addAll(l_list);
		execLibs.addAll(BND.EXECLIB);
		members.putAll(ExecLib.fileMap(l_list));
	}

	/**
	 * Validates and loads the configuration file for the next job step.
	 * 
	 * @param aMember the base name of the configuration file
	 * @return the step configuration
	 */
	public Config load(String aMember) {
		if (!stepList.contains(aMember)) {
			File l_file = members.get(aMember);

			if (l_file != null) {
				Config l_ret = ConfigFactory.parseFile(l_file);

				if (l_ret.hasPath(EXEC)) {
					stepList.add(aMember);

					return l_ret;
				}
				throw exception(MSG.EXEC_MISSING, aMember);
			}
			throw exception(MSG.FILE_NOT_FOUND, aMember, execLibs);
		}
		throw exception(MSG.JOB_CYCLE_FOUND, aMember);
	}
}
