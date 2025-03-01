/* --------------------------------------------------------------------------
 * Project: OASE - Open Application Service Engine Framework
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved.
 * -------------------------------------------------------------------------- */

package biz.oase.car;

import java.io.File;
import java.io.IOException;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;
import java.util.function.Supplier;

import org.osgi.service.cm.Configuration;
import org.osgi.service.cm.ConfigurationAdmin;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import biz.car.SYS;
import biz.car.config.XConfig;
import biz.car.io.FSObject;

/**
 * Provides functions to use the OSGi configuration admin service.<br>
 * This configuration object related functions are valid only for configuration
 * objects with values of type <code>String</code>.
 *
 * @version 1.0.0 07.02.2025 15:03:05
 */
public interface XConfigAdmin extends Supplier<ConfigurationAdmin> {

	/**
	 * Get an existing or new {@code Configuration} object from the persistent
	 * store.
	 *
	 * If the {@code Config} object for this PID does not exist, create a new empty
	 * {@code Config} object for that PID.
	 *
	 * @param aPID the persistent identifier.
	 * @return an existing or new {@code Config} matching the PID.
	 */
	default public Config getConfig(String aPID) {
		try {
			Config l_ret = XConfig.EMPTY;
			ConfigurationAdmin l_adm = get();
			Configuration l_cfg = l_adm.getConfiguration(aPID, null);
			Dictionary<String, Object> l_props = l_cfg.getProperties();

			if (l_props != null) {
				l_ret = XConfig.fromDictionary(l_props);
			}
			return l_ret;
		} catch (IOException anEx) {
			throw SYS.LOG.exception(anEx);
		}
	}

	/**
	 * Update the properties of the configuration object identified by the given
	 * PID.
	 *
	 * @param aPID    the persistent identifier.
	 * @param aConfig the new set of properties for the configuration
	 */
	default public void update(String aPID, Config aConfig) {
		try {
			ConfigurationAdmin l_adm = get();
			Configuration l_cfg = l_adm.getConfiguration(aPID, null);
			Map<String, String> l_map = XConfig.toStringMap(aConfig);
			Hashtable<String, String> l_dict = new Hashtable<String, String>(l_map);

			l_cfg.update(l_dict);
		} catch (IOException anEx) {
			throw SYS.LOG.exception(anEx);
		}
	}

	/**
	 * Update the properties of a configuration object.<br>
	 * The base name of the file is taken as the PID of the configuration object.
	 * The properties are loaded from the file.
	 *
	 * @param aFile the file holding the properties of the configuration object.
	 */
	default public void update(File aFile) {
		FSObject l_fso = () -> aFile;
		String l_pid = l_fso.getBaseName();
		Config l_conf = ConfigFactory.parseFile(aFile);
		
		update(l_pid, l_conf);
	}
}
