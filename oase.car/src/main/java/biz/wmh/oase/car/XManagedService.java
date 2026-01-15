/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Common Application Runtime
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved.
 * -------------------------------------------------------------------------- */

package biz.wmh.oase.car;

import java.util.Dictionary;

import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;

import biz.wmh.car.config.ConfigAdapter;
import biz.wmh.car.config.XConfig;

/**
 * Base class for implementing an OSGi <code>ManagedService</code>.
 * 
 * @version 2.0.0 14.01.2026 19:35:51
 */
public abstract class XManagedService
	extends ConfigAdapter
	implements ManagedService {

	/**
	 * Creates a default <code>ManagedTimerTask</code> instance.
	 */
	public XManagedService() {
		super();
	}

	/**
	 * Performs the actions on receiving a configuration when it is registered or
	 * modified.
	 * 
	 * @param aProps the configuration items
	 * @throws ConfigurationException
	 */
	@Override
	public void updated(Dictionary<String, ?> aProps)
		throws ConfigurationException {
		if (aProps == null) {
			disabledService();

			return;
		}
		accept(XConfig.fromDictionary(aProps));
		updatedService();
	}

	/**
	 * Performs the actions on receiving an empty configuration.
	 */
	protected abstract void disabledService();

	/**
	 * Performs the actions on receiving a configuration when it is registered or
	 * modified.
	 */
	protected abstract void updatedService();
}
