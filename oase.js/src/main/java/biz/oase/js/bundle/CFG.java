/* --------------------------------------------------------------------------
 * Project: OASE Job Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.js.bundle;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import biz.car.config.XConfig;
import biz.oase.car.OASE;
import biz.oase.js.inbox.Inbox;

/**
 * Job Service Runtime Configuration.
 *
 * @version 2.0.0 16.03.2026 15:12:00
 */
@Component(
	property = {
		"service.pid=oase.jobservice" },
	immediate = true)
@Designate(ocd = CFG.Options.class)
public class CFG {

	@ObjectClassDefinition(
		name = "OASE Job Service Configuration",
		description = "Configuration for the runtime options of the OASE Job Service")
	public @interface Options {
		@AttributeDefinition(
			name = "Inbox",
			description = "The name of the inbox folder")
		String inbox() default "exec/inbox";

		@AttributeDefinition(
			name = "Job Library",
			description = "The folder name of the job library")
		String joblib() default "exec/job, exec/step";

		@AttributeDefinition(
			name = "Outbox",
			description = "The name of the outbox folder")
		String outbox() default "exec/outbox";

		@AttributeDefinition(
			name = "Timer Interval",
			description = "The polling interval of the inbox timer")
		String timerInterval() default "2s";
	}

	public static List<String> EXECLIB;

	private Inbox in;

	/**
	 * Creates a default <code>BND</code> instance.
	 */
	public CFG() {
		super();
	}

	@Activate
	protected void activate(Options aConfig) throws Exception {
		Map<String, String> l_map = new HashMap<String, String>();

		l_map.put("joblib", aConfig.joblib()); //$NON-NLS-1$
		l_map.put("inbox", aConfig.inbox()); //$NON-NLS-1$
		l_map.put("outbox", aConfig.outbox()); //$NON-NLS-1$
		l_map.put(OASE.PERIOD, aConfig.timerInterval());

		Config l_conf = ConfigFactory.parseMap(l_map);
		XConfig l_xc = () -> l_conf;
		EXECLIB = l_xc.asStringList("joblib"); //$NON-NLS-1$
		in = new Inbox(l_conf);

		in.start();
	}

	@Deactivate
	protected void deactivate() {
		in.stop();

		in = null;
	}
}
