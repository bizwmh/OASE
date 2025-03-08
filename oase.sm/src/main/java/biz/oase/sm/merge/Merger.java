/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.merge;

import com.typesafe.config.Config;

import biz.oase.sm.core.Group;
import biz.oase.sm.core.Procedure;

/**
 * Implementation of the standard merge procedure.
 *
 * @version 1.0.0 08.03.2025 15:05:01
 */
public class Merger extends Procedure {

	private String eof;
	private GroupController groupCtl;
	private InputController inputCtl;
	private Group procedureGroup;

	/**
	 * Creates a default <code>Merger</code> instance.
	 */
	public Merger() {
		super();
	}

	@Override
	public void accept(Config aConfig) {
		super.accept(aConfig);

		inputCtl = new InputController();
		procedureGroup = context().procedureGroup();
		eof = procedureGroup.endOfInput();

		openInput();
		openOutput();
		inputCtl.visit(context());
		groupCtl = GroupController.build(procedureGroup, inputCtl);
	}

	@Override
	public void dispose() {
		if (inputCtl != null) {
			inputCtl.dispose();

			inputCtl = null;
		}
		closeInput();
		closeOutput();
		super.dispose();

		groupCtl = null;
	}

	@Override
	protected void doMain() {
		groupCtl.run();
	}

	@Override
	protected void exit() {
		// nothing to do
	}

	@Override
	protected boolean hasInput() {
		return procedureGroup.getValue() != eof;
	}
//
//	public final ClientRegistry CR = new ClientRegistry();

	@Override
	protected void init() {
		// load first record from each input channel
		inputCtl.list().forEach(p -> p.readNextRecord());
		// select first input
		inputCtl.select();
		procedureGroup.copyOf(inputCtl.selectedInputGroup());
	}
}
