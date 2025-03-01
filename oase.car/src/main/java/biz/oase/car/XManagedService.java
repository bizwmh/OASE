/* --------------------------------------------------------------------------
 * Project: OASE - Open Application Service Engine Framework
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved.
 * -------------------------------------------------------------------------- */

package biz.oase.car;

import java.util.Dictionary;

import org.osgi.service.cm.ConfigurationException;
import org.osgi.service.cm.ManagedService;

import biz.car.config.ConfigObject;
import biz.car.config.XConfig;

/**
 * Base class for implementing an OSGi <code>ManagedService</code>.
 * 
 * @version 1.0.0 07.02.2025 15:01:22
 */
public abstract class XManagedService
	extends ConfigObject
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
