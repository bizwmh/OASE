/* --------------------------------------------------------------------------
 * Project: OASE - Open Application Service Engine Framework
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved.
 * -------------------------------------------------------------------------- */

package biz.oase.car;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.dto.BundleDTO;

import biz.car.CAR;
import biz.oase.car.bundle.CA;
import biz.oase.car.bundle.VAR;

/**
 * OASE constants.
 *
 * @version 1.0.0 04.11.2024 10:04:32
 */
public interface OASE extends CAR {

	/**
	 * A reference to the OSGi Configuration Admin service.
	 */
	XConfigAdmin CAS = CA.instance;

	/**
	 * The version number of the OASE framework.
	 */
	String VERSION = getVersion();

	/**
	 * The name of the ASGi workspace.
	 */
	String WORKSPACE = getWorkspace();

	private static String getVersion() {
		Bundle l_bundle = FrameworkUtil.getBundle(OASE.class);
		BundleDTO l_dto = l_bundle.adapt(BundleDTO.class);

		return l_dto.version;
	}

	private static String getWorkspace() {
		Bundle l_bundle = FrameworkUtil.getBundle(OASE.class);
		String l_key = VAR.WORKSPACE;
		String l_ret = l_bundle.getBundleContext().getProperty(l_key);

		return l_ret;
	}
}