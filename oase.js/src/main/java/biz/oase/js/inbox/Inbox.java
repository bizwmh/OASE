/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Job Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.js.inbox;

import java.io.File;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.typesafe.config.Config;

import biz.car.io.XDirectory;
import biz.car.util.XTimertask;
import biz.oase.car.OASE;

/**
 * Performs the processing of the inbox folder.<br>
 * Each file found is prefixed with a timestamp and then moved to the outbox
 * folder. From here the job is started.
 *
 * @version 2.0.0 10.11.2025 15:24:11
 */
public class Inbox extends XTimertask implements OASE {

	private String inbox;
	private String outbox;
	private Predicate<File> properties = f -> f.getName().endsWith(_properties);
	private Predicate<InboxFile> rename = f -> f.rename();
	private Consumer<InboxFile> submit = f -> f.submit();

	/**
	 * Creates a default <code>Inbox</code> instance.
	 * @param aConfig the underlying configuration
	 */
	public Inbox(Config aConfig) {
		super(aConfig);
		
		checkExists(inbox);
		checkExists(outbox);
	}

	@Override
	public void run() {
		jobFiles().forEach(submit);
	}

	private void checkExists(String aFile) {
		File l_file = new File(aFile);
		
		if (!l_file.exists()) {
			l_file.mkdirs();
		}
	}

	private List<InboxFile> jobFiles() {
		File l_file = new File(inbox);

		XDirectory l_dir = new XDirectory(l_file);
		List<InboxFile> l_ret = l_dir.fileList(properties).stream()
				.map(InboxFile::new)
				.filter(rename)
				.filter(file -> file.move(outbox))
				.collect(Collectors.toList());

		return l_ret;
	}
}
