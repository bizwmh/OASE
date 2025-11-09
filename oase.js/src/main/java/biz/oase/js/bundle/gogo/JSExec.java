/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Job Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.js.bundle.gogo;

import java.io.File;
import java.util.List;
import java.util.Optional;

import org.apache.felix.service.command.Descriptor;
import org.apache.felix.service.command.annotations.GogoCommand;
import org.osgi.service.component.annotations.Component;

import biz.car.XLogger;
import biz.oase.js.ExecLib;
import biz.oase.js.JobEntryService;
import biz.oase.js.bundle.CFG;
import biz.oase.js.bundle.MSG;

/**
 * Console Command: exec
 *
 * @version 2.0.0 04.11.2025 16:45:48
 */
@GogoCommand(scope = "oasejs", function = "exec")
@Component(service = JSExec.class)
public class JSExec {

	@Descriptor("Submits a job for execution")
	public String exec(String aName) {
		Optional<File> l_file = ExecLib.get(aName);
		String l_ret = null;

		if (l_file.isPresent()) {
			File l_jobFile = l_file.get();

			JobEntryService.submit(l_jobFile);

			l_ret =  XLogger.format(MSG.JOB_SUBMITTED, l_jobFile.getPath());
		} else {
			List<String> l_libs = CFG.EXECLIB;
			l_ret = XLogger.format(MSG.FILE_NOT_FOUND, aName, l_libs);
		}
		return l_ret;
	}
}