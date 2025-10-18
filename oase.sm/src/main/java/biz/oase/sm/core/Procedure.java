/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.core;

import com.typesafe.config.Config;

import biz.car.XRunnable;
import biz.car.config.ConfigAdapter;
import biz.oase.sm.bundle.MSG;
import biz.oase.sm.context.ProcedureContext;

/**
 * A <code>Procedure</code> can either be a sort or a merge procedure.
 * <p>
 * This class implements the basic functional structure of an SM procedure. It
 * consists of
 * <ul>
 * <li>open input channels
 * <li>open output channels
 * <li>initialize procedure
 * <li>execute main process
 * <li>exit procedure or abend procedure (in case of an exception)
 * <li>close input channels
 * <li>close output channels
 * <li>release all allocated resources
 * </ul>
 *
 * @version 1.0.0 08.03.2025 14:37:53
 */
public abstract class Procedure extends ConfigAdapter implements XRunnable {

	private ProcedureContext ctx;

	/**
	 * Creates a default <code>SMProcedure</code> instance.
	 */
	public Procedure() {
		super();
	}

	@Override
	public void accept(Config aConfig) {
		ctx = new ProcedureContext();

		super.accept(aConfig);
		ctx.accept(aConfig);
		info(MSG.EXEC_INITIALIZED, getLabel(), getName());
	}

	/**
	 * @return the runtime context for this SM procedure
	 */
	public ProcedureContext context() {
		return ctx;
	}

	@Override
	public void dispose() {
		if (ctx != null) {
			ctx.dispose();

			ctx = null;
		}
	}

	@Override
	public void exec() {
		init();
		main();
		exit();
	}

	/**
	 * Disconnects from all open input channels.<br>
	 * Allocated resources are released.
	 */
	protected void closeInput() {
		ctx.inputNames().stream()
				.map(ctx::getInput)
				.forEach(Input::close);
	}

	/**
	 * Disconnects from all open output channels.<br>
	 * Allocated resources are released.
	 */
	protected void closeOutput() {
		ctx.outputNames().stream()
				.map(ctx::getOutput)
				.forEach(Output::close);
	}

	/**
	 * Executes the main processing of the SM procedure.
	 */
	protected abstract void doMain();

	/**
	 * Finalizes the main processing.<br>
	 * This method is invoked once after the completion of the <b>main</b> function.
	 */
	protected abstract void exit();

	/**
	 * Checks if input records are still to be processed.
	 * 
	 * @return <code>true</code> if there are still input records to be processed
	 */
	protected abstract boolean hasInput();

	/**
	 * Initializes the main processing.<br>
	 * This method is invoked once before the <code>main</code> function.
	 */
	protected abstract void init();

	/**
	 * Opens input channels.
	 */
	protected void openInput() {
		if (ctx.inputNames().size() == 0) {
			throw exception(MSG.NO_INPUT_CHANNELS, getLabel(), getName());
		}
		ctx.inputNames().stream()
				.map(ctx::getInput)
				.forEach(Input::open);
	}

	/**
	 * Opens output channels.
	 */
	protected void openOutput() {
		ctx.outputNames().stream()
				.map(ctx::getOutput)
				.forEach(Output::open);
	}

	private void main() {
		while (hasInput()) {
			doMain();
		}
	}
}
