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
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import biz.car.VAL;
import biz.car.XLogger;
import biz.car.io.FSObject;
import biz.car.io.XDirectory;
import biz.oase.js.bundle.MSG;

/**
 * Converts the name of a job configuration to the corresponding configuration
 * object.
 *
 * @version 1.0.0 11.02.2025 12:20:10
 */
@FunctionalInterface
public interface ExecLib extends
		Supplier<List<String>>, Function<String, Optional<File>> {

	@Override
	default Optional<File> apply(String aName) {
		List<String> l_libs = get();
		File l_ret = null;

		for (String l_lib : l_libs) {
			File l_file = new File(l_lib, aName + VAL._properties);

			if (l_file.isFile()) {
				l_ret = l_file;

				break;
			}
		}
		return Optional.ofNullable(l_ret);
	}

	/**
	 * @return the map of all property files located in the folders of EXECLIB.
	 */
	default Map<String, File> fileMap() {
		List<String> l_libs = get();
		List<FSObject> l_list = l_libs.stream()
				.map(name -> new XDirectory(name))
				.map(dir -> dir.allFiles(f -> f.getName().endsWith(VAL._properties)))
				.flatMap(list -> list.stream())
				.map(file -> {
					FSObject l_fso = () -> file;
					return l_fso;
				})
				.collect(Collectors.toList());
		Map<String, File> l_ret = new HashMap<String, File>();

		l_list.forEach(fso -> {
			String l_key = fso.getBaseName();

			if (l_ret.containsKey(l_key)) {
				String l_msg = XLogger.format(MSG.DUPLICATE_EXEC_MEMBER, l_key, l_libs.toString());

				throw new IllegalArgumentException(l_msg);
			} else {
				File l_value = fso.get();

				l_ret.put(l_key, l_value);
			}
		});
		return l_ret;
	}
}
