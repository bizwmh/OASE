package biz.oase.tr.core;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadFactory;
import java.util.function.Consumer;

import org.slf4j.Logger;

import com.typesafe.config.Config;

import com.viessmann.vas.commons.MSG;
import com.viessmann.vas.commons.VAS;
import com.viessmann.vas.commons.VASException;
import com.viessmann.vas.commons.XLogger;

/**
 * General purpose interface for executing a VAS scenario.
 * <p>
 * Typical use of a <code>Scenario</code> instance is
 * <ul>
 * <li>call <code>accept(Config)</code><br>
 * Allows the scenario to initialize itself based on the given configuration
 * <li>call <code>run</code><br>
 * Starts the execution of the scenario
 * </ul>
 *
 * @version 1.0.0 18.08.2023 14:09:46
 */
public interface Scenario extends
	Consumer<Config>,
	Runnable,
	ThreadFactory,
	XLogger {

	/**
	 * Executed once after successful invocation of the <code>run</code> method.
	 */
	void afterRun();

	/**
	 * Executed once before <code>run</code> is invoked.
	 */
	void beforeRun();

	/**
	 * Releases all allocated resources.
	 */
	void dispose();

	/**
	 * Executes the action items of this scenario.
	 */
	void doRun();

	/**
	 * @return the label of the scenario
	 */
	default String getLabel() {
		return getClass().getSimpleName();
	}

	/**
	 * @return the name of the scenario.
	 */
	default String getName() {
		return getClass().getSimpleName();
	}

	@Override
	default Logger logger() {
		return VAS.LOG.logger();
	}

	@Override
	default Thread newThread(Runnable aTask) {
		Thread l_ret = new Thread(aTask);

		l_ret.setDaemon(true);

		return l_ret;
	}

	@Override
	default void run() {
		try {
			Thread.currentThread().setName(getName());
			info(MSG.SCENARIO_STARTED, getLabel(), getName());
			beforeRun();
			doRun();
			afterRun();
			info(MSG.SCENARIO_ENDED, getLabel(), getName());
		} catch (VASException anEx) {
			error(MSG.SCENARIO_ABENDED, getLabel(), getName());
		} catch (Throwable anEx) {
			exception(anEx);
			error(MSG.SCENARIO_ABENDED, getLabel(), getName());
		} finally {
			dispose();
		}
	}

	/**
	 * Starts this scenario as a thread.
	 * 
	 * @return the started thread instance.
	 */
	default CompletableFuture<Void> start() {
		CompletableFuture<Void> l_ret = CompletableFuture.runAsync(this);

		return l_ret;
	}
}
