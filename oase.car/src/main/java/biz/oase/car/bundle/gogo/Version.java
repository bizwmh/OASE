/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Framework
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved.
 * -------------------------------------------------------------------------- */

package biz.oase.car.bundle.gogo;

import org.apache.felix.service.command.Descriptor;
import org.apache.felix.service.command.annotations.GogoCommand;
import org.osgi.service.component.annotations.Component;

import biz.oase.car.OASE;

/**
 * Console Command: version
 *
 * @version 1.0.0 08.02.2025 12:45:49
 */
@GogoCommand(scope = "asgi", function = "version")
@Component(service = Version.class)
public class Version {

	@Descriptor("Displays the version of the ASGI framework")
	public String version() {
		return OASE.VERSION;
	}
}