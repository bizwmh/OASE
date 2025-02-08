/* --------------------------------------------------------------------------
 * Project: OASE - Open Application Service Engine Framework
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved.
 * -------------------------------------------------------------------------- */

package biz.oase.framework.bundle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.osgi.service.cm.ConfigurationAdmin;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import biz.car.SYS;
import biz.car.config.ACS;
import biz.oase.framework.XComponent;
import biz.oase.framework.XConfigAdmin;

/**
 * Implementation of the <code>XConfigAdmin</code> interface.
 *
 * @version 1.0.0 07.02.2025 15:08:25
 */
@Component(immediate = true)
public class CA extends XComponent implements XConfigAdmin {

	public static String CONFIG_AREA;
	public static CA instance;
	
	static {
		ACS.initialize(CA.class, ACS.APP.config());
	}

	@Reference
	private ConfigurationAdmin ca;

	/**
	 * Creates a default <code>CA</code> instance.
	 */
	public CA() {
		super();
	}

	@Override
	public ConfigurationAdmin get() {
		return ca;
	}

	@Override
	protected void doActivate() {
		instance = this;
		Path l_path = Paths.get(CONFIG_AREA);

		try {
			Files.list(l_path)
					.map(p -> p.toFile())
					.filter(f -> f.isFile() && f.getName().endsWith(".cfg")) //$NON-NLS-1$
					.forEach(this::update);
		} catch (IOException anEx) {
			throw SYS.LOG.exception(anEx);
		}
	}

	@Override
	protected void doDeactivate() {
		instance = null;
	}
}
