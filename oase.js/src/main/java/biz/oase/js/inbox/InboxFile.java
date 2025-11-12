/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Job Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.js.inbox;

import static biz.car.io.PrefixedFile.isPrefixed;
import static biz.car.io.PrefixedFile.trimPrefix;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import biz.car.SYS;
import biz.car.io.PrefixedFile;
import biz.oase.js.JobEntryService;

/**
 * Wrapper for a job configuration file of the inbox folder.
 *
 * @version 2.0.0 10.11.2025 15:23:03
 */
public class InboxFile {

	private File myFile;
	private File prefixed;

	/**
	 * Creates a default <code>InboxFile</code> instance.
	 * 
	 * @param aFile the inbox file
	 */
	public InboxFile(File aFile) {
		super();

		File l_file = aFile;
		String l_name = aFile.getName();
		String l_parent = aFile.getParent();

		if (isPrefixed(l_name)) {
			l_name = trimPrefix(l_name);
			l_file = new File(l_parent, l_name);

			aFile.renameTo(l_file);
		}
		myFile = l_file;
		PrefixedFile l_pf = () -> myFile;
		prefixed = l_pf.prefix();
	}

	/**
	 * Moves the prefixed file from the inbox to the outbox folder.
	 * 
	 * @param aFolder the path to the outboux folder
	 * @return true if move was successful
	 */
	public boolean move(String aFolder) {
		File l_outfile = new File(aFolder, prefixed.getName());
		Path l_in = prefixed.toPath();
		Path l_out = l_outfile.toPath();

		try {
			Path l_to = Files.move(l_in, l_out, StandardCopyOption.ATOMIC_MOVE);
			prefixed = l_to.toFile();

			return true;
		} catch (IOException anEx) {
			SYS.LOG.error(anEx);
			return false;
		}
	}

	/**
	 * Renames the file to a file prefixed with a time stamp.<br>
	 * If the file name is already prefixed the current prefix is replaced by a new
	 * one.
	 * 
	 * @return <code>true</code> if the file could be renamed
	 */
	public boolean rename() {
		boolean l_ret = myFile.renameTo(prefixed);

		return l_ret;
	}

	/**
	 * Submits the file in the outbox for execution.
	 */
	public void submit() {
		JobEntryService.submit(prefixed);
	}
}
