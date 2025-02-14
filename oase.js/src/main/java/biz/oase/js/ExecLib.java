/* --------------------------------------------------------------------------
 * Project: OASE Job Service
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
import biz.oase.js.bundle.BND;
import biz.oase.js.bundle.MSG;

/**
 * Provides access to the files in the EXECLIB folders.
 *
 * @version 1.0.0 14.02.2025 12:12:58
 */
public interface ExecLib {

	/**
	 * Builds a map of all files located in the given list of folders
	 * 
	 * @param aList the list of folders
	 * @return the map of files
	 * @throws IllegalArgumentException if a duplicate member is found
	 */
	static Map<String, File> fileMap(List<String> aList) {
		List<FSObject> l_list = listFiles(aList);
		Map<String, File> l_ret = new HashMap<String, File>();

		l_list.forEach(fso -> {
			String l_key = fso.getBaseName();

			if (l_ret.containsKey(l_key)) {
				String l_msg = XLogger.format(MSG.DUPLICATE_EXEC_MEMBER, l_key, aList.toString());

				throw new IllegalArgumentException(l_msg);
			} else {
				File l_value = fso.get();

				l_ret.put(l_key, l_value);
			}
		});
		return l_ret;
	}

	/**
	 * Looks up a file in the EXECLIB.
	 * 
	 * @param aName the base name of the file
	 * @return the optional file
	 */
	static Optional<File> get(String aName) {
		List<FSObject> l_list = listFiles(BND.EXECLIB);
		File l_ret = null;

		for (FSObject l_fso : l_list) {
			if (l_fso.getBaseName().equals(aName)) {
				l_ret = l_fso.get();

				break;
			}
		}
		return Optional.ofNullable(l_ret);
	}

	/**
	 * Builds a list of all files located in the given list of folders
	 * 
	 * @param aList the list of folders
	 * @return the list of all files located in the folders of EXECLIB.
	 */
	static List<FSObject> listFiles(List<String> aList) {
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
}
