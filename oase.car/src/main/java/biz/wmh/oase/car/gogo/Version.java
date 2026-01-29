/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Common Application Runtime
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved.
 * -------------------------------------------------------------------------- */

package biz.wmh.oase.car.gogo;

import org.apache.felix.service.command.Descriptor;
import org.apache.felix.service.command.annotations.GogoCommand;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.dto.BundleDTO;
import org.osgi.service.component.annotations.Component;

/**
 * Console Command: version
 *
 * @version 2.0.0 17.10.2025 16:57:59
 */
@GogoCommand(scope = "oase", function = "version")
@Component(service = Version.class)
public class Version {

	@Descriptor("Displays the version of the OASE Framework")
	public String version() {
		Bundle l_bundle = FrameworkUtil.getBundle(Version.class);
		BundleDTO l_dto = l_bundle.adapt(BundleDTO.class);

		return l_dto.version;
	}
}