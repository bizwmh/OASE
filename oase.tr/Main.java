package biz.oase.tr.core;

import com.viessmann.vas.commons.VAS;
import com.viessmann.vas.commons.VASException;

/**
 * Entry point for a VAS simulation run.
 *
 * @version 1.0.0 01.08.2023 08:24:18
 */
public class Main {

	/**
	 * Starts the initial VAS scenario.
	 * 
	 * @param anArgs the name of the scenario configuration.
	 */
	public static void main(String[] anArgs) {
		// make sure exactly 1 argument is passed
		// the argument is the name of the scenario configuration
		if (anArgs.length != 1) {
			System.out.println();
			System.out.println("usage: VAS<n> <file>"); //$NON-NLS-1$
			System.out.println();
			System.out.println("<n> the number of the environment layer"); //$NON-NLS-1$
			System.out.println("<file> the scenario configuration file name"); //$NON-NLS-1$

			System.exit(1);
		}

		// use Launcher to start the scenario
		try {
			Launcher.submit(anArgs[0]).join();
		} catch (VASException anEx) {
		} catch (Exception anEx) {
			VAS.LOG.exception(anEx);
		}
	}

	private Main() {
		super();
	}
}
