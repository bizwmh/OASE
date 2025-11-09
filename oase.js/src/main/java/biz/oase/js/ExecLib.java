/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Job Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.js;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import biz.car.XLogger;
import biz.car.io.FSObject;
import biz.car.io.XDirectory;
import biz.oase.js.bundle.CFG;
import biz.oase.js.bundle.MSG;

/**
 * Provides access to the files in the EXECLIB folders.
 *
 * @version 2.0.0 06.11.2025 08:44:43
 */
public class ExecLib {

	private static Map<String, File> fileMap;

	static {
		fileMap = toMap(CFG.EXECLIB);
	}

	/**
	 * Looks up a file in the EXECLIB.
	 * 
	 * @param aName the base name of the file
	 * @return the optional file
	 */
	public static Optional<File> get(String aName) {
		File l_ret = fileMap.get(aName);

		return Optional.ofNullable(l_ret);
	}

	/**
	 * Builds a list of all files located in the given list of folders
	 * 
	 * @param aList the list of folders
	 * @return the list of all files located in the folders of EXECLIB.
	 */
	private static List<FSObject> listFiles(List<String> aList) {
		List<FSObject> l_ret = aList.stream()
				.map(name -> new XDirectory(name))
				.map(dir -> dir.allFiles())
				.flatMap(list -> list.stream())
				.map(file -> {
					FSObject l_fso = () -> file;
					return l_fso;
				})
				.collect(Collectors.toList());
		return l_ret;
	}

	/**
	 * Builds a map of all files located in the given list of folders
	 * 
	 * @param aList the list of folders
	 * @return the map of files
	 * @throws IllegalArgumentException if a duplicate member is found
	 */
	private static Map<String, File> toMap(List<String> aList) {
		List<FSObject> l_list = listFiles(aList);
		Map<String, File> l_ret = new HashMap<String, File>();

		l_list.forEach(fso -> {
			String l_key = fso.getBaseName();

			if (l_ret.containsKey(l_key)) {
				String l_msg = XLogger.format(MSG.DUPLICATE_LIB_MEMBER, l_key, aList.toString());

				throw new IllegalArgumentException(l_msg);
			} else {
				File l_value = fso.get();

				l_ret.put(l_key, l_value);
			}
		});
		return l_ret;
	}
}
