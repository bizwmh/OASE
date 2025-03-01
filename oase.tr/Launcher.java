package biz.oase.tr.core;

import static com.viessmann.vas.commons.VAL.Job;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import com.viessmann.vas.commons.ClassUtil;
import com.viessmann.vas.commons.ConfigParser;
import com.viessmann.vas.commons.MSG;
import com.viessmann.vas.commons.VAL;
import com.viessmann.vas.commons.VAR;
import com.viessmann.vas.commons.VAS;
import com.viessmann.vas.commons.VASException;

/**
 * Launcher for a VAS simulation run.
 * 
 * @version 1.0.0 23.01.2024 14:17:59
 */
public class Launcher {

	// Config for the scenario class names
	private static Config conf;
	// executed scenario names
	private static List<String> scnNames;

	static {
		conf = ConfigFactory.parseResources(VAL.Job + VAL._conf);
		scnNames = new ArrayList<>();
	}

	/**
	 * Starts the execution of a VAS scenario.
	 * <p>
	 * First Job tries to load the scenario configuration.<br>
	 * After successful load the execution of the scenario is started.
	 * 
	 * @param aConfName the base name of the VAS scenario configuration file.
	 *                  The extension '.conf' is appended by the
	 *                  <code>Launcher</code>.
	 * @return the <code>Future</code> running the VAS scenario
	 */
	public static CompletableFuture<Void> submit(String aConfName) {
		try {
			// Create the scenario for the job

			Optional<Scenario> l_opt = scenario(aConfName);
			// Terminate if config file does not exist
			Scenario l_scn = l_opt.orElseThrow(VASException::new);
			// Start the scenario job thread
			CompletableFuture<Void> l_ret = l_scn.start();

			return l_ret;
		} catch (VASException anEx) {
			throw anEx;
		} catch (Exception anEx) {
			throw VAS.LOG.exception(anEx);
		}
	}

	/**
	 * Factory method for a VAS job.
	 * <p>
	 * Tries to load the scenario configuration:
	 * <ul>
	 * <li>the configuration must be found as a resource on the class path
	 * <li>the scenario configuration is not invoked for the second time<br>
	 * (to avoid a loop cycle)
	 * </ul>
	 * After successful load the job is initialized with the configuration.
	 * 
	 * @param aConfName the base name of the VAS scenario configuration file.
	 *                  The extension '.conf' is appended by the Job.
	 * @return the resulting optional <code>Scenario</code> instance
	 */
	private static Optional<Scenario> scenario(String aConfName) {
		Scenario l_ret = null;

		try {
			// check if a scenario with the given name has already been started
			if (!scnNames.contains(aConfName)) {
				// Initialize and start the job
				VAS.LOG.info(MSG.SCENARIO_INITIALIZING, Job, aConfName);

				Config l_conf = ConfigParser.parse(aConfName);

				if (l_conf.hasPath(VAR.MAIN)) {
					// Create the Job instance based on Main parameter
					String l_main = l_conf.getString(VAR.MAIN);
					Scenario l_scn = scenarioJob(aConfName, l_main);

					// convert l_conf to XConfig instance
					l_scn.accept(l_conf);
					scnNames.add(aConfName);

					// everything ok, now we can set the return value
					l_ret = l_scn;
				} else {
					VAS.LOG.error(MSG.MAIN_FUNCTION_MISSING);
				}
			} else {
				VAS.LOG.error(MSG.SCENARIO_MORE_THAN_ONCE, aConfName);
			}
		} catch (VASException anEx) {
		} catch (Throwable anEx) {
			VAS.LOG.exception(anEx);
		}
		return Optional.ofNullable(l_ret);
	}

	private static Scenario scenarioJob(String aConfig, String aMain)
		throws Exception {
		String l_cn = conf.getString(aMain);
		Class<?> l_class = Class.forName(l_cn);
		Scenario l_ret = ClassUtil.newInstance(l_class, aConfig);

		return l_ret;
	}

	private Launcher() {
		super();
	}
}
