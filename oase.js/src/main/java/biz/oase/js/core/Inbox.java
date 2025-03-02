/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Job Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.js.core;

import static biz.car.VAL._properties;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import biz.car.io.XDirectory;
import biz.car.util.XTimertask;
import biz.oase.js.bundle.VAR;

/**
 * Performs the processing of the inbox folder.<br>
 * Each file found is prefixed with a timestamp and then moved to the outbox
 * folder. From here the job is started.
 *
 * @version 1.0.0 11.02.2025 14:17:33
 */
public class Inbox extends XTimertask {

	private Predicate<File> properties = f -> f.getName().endsWith(_properties);
	private Predicate<ExecFile> rename = f -> f.rename();
	private Consumer<ExecFile> submit = f -> f.submit();

	/**
	 * Creates a default <code>Inbox</code> instance.
	 */
	public Inbox() {
		super();
	}

	@Override
	public void run() {
		jobFiles().forEach(submit);
	}

	private List<ExecFile> jobFiles() {
		String l_in = getString(VAR.INBOX);
		String l_out = getString(VAR.OUTBOX);
		File l_file = new File(l_in);

		XDirectory l_dir = new XDirectory(l_file);
		List<ExecFile> l_ret = l_dir.fileList(properties).stream()
				.map(ExecFile::new)
				.filter(rename)
				.filter(file -> file.move(l_out))
				.collect(Collectors.toList());

		return l_ret;
	}
}
