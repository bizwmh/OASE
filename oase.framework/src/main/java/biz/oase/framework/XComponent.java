/* --------------------------------------------------------------------------
 * Project: OASE - Open Application Service Engine Framework
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved.
 * -------------------------------------------------------------------------- */

package biz.oase.framework;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentConstants;
import org.osgi.service.component.ComponentContext;

import com.typesafe.config.Config;

import biz.car.XRuntimeException;
import biz.car.config.ConfigObject;
import biz.car.config.XConfig;
import biz.oase.framework.bundle.MSG;

/**
 * Convenience class for a component managed by the Service Component Runtime.
 *
 * @version 1.0.0 07.02.2025 15:03:25
 */
public abstract class XComponent
		extends ConfigObject
		implements OASE {

	private BundleContext bctx;

	/**
	 * Creates a default <code>XComponent</code> instance.
	 */
	public XComponent() {
		super();
	}

	/**
	 * Activates this component.
	 * 
	 * @param aContext the OSGi component context for this component
	 */
	public void activate(ComponentContext aContext) {
		bctx = aContext.getBundleContext();
		Config l_conf = XConfig.fromDictionary(aContext.getProperties());
		
		accept(l_conf);

		String l_cn = getString(ComponentConstants.COMPONENT_NAME);

		try {
			info(MSG.COMPONENT_ACTIVATING, l_cn);
			doActivate();
			info(MSG.COMPONENT_ACTIVATED, l_cn);
		} catch (XRuntimeException anEx) {
			throw anEx;
		} catch (Throwable anEx) {
			throw exception(anEx, MSG.COMPONENT_ACTIVATION_FAILED, l_cn);
		}
	}

	/**
	 * Deactivates this component.
	 */
	public void deactivate() {
		String l_cn = getString(ComponentConstants.COMPONENT_NAME);

		info(MSG.COMPONENT_DEACTIVATING, l_cn);
		doDeactivate();
		info(MSG.COMPONENT_DEACTIVATED, l_cn);
	}

	/**
	 * @return the bundle context for this component
	 */
	public BundleContext getBundleContext() {
		return bctx;
	}

	/**
	 * Performs the actions to activate this component.
	 */
	protected abstract void doActivate();
	
	/**
	 * Performs the actions to deactivate this component.
	 */
	protected abstract void doDeactivate();
}
