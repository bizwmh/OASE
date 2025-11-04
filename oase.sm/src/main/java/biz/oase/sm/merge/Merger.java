/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.merge;

import java.util.List;

import com.typesafe.config.Config;

import biz.oase.sm.core.Group;
import biz.oase.sm.core.Procedure;

/**
 * Implementation of the standard merge procedure.
 *
 * @version 1.0.0 22.10.2025 09:24:59
 */
public class Merger extends Procedure {

	private ClientManager clMgr;
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

		clMgr = new ClientManager();
		inputCtl = new InputController();
		procedureGroup = context().newGroup();
		eof = procedureGroup.endOfInput();

		procedureGroup.visit(context());
		inputCtl.visit(context(), clMgr);
	}

	@Override
	public void dispose() {
		if (procedureGroup != null) {
			procedureGroup.dispose();

			procedureGroup = null;
		}
		if (inputCtl != null) {
			inputCtl.dispose();

			inputCtl = null;
		}
		if (groupCtl != null) {
			groupCtl.dispose();

			groupCtl = null;
		}
		closeInput();
		closeOutput();

		super.dispose();
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

	@Override
	protected void init() {
		// open io channels
		openInput();
		openOutput();
		// load first record from each input channel
		// initialize input groups
		// select first input group for processing
		Group l_inputGroup = inputCtl.firstInputGroup();
		// build the list of group controllers
		// use top level group controller for controlling the merge procedure
		groupCtl = buildGroupController(l_inputGroup);
		// initialize procedure group
		procedureGroup.copyOf(l_inputGroup);
	}

	/**
	 * Creates a <code>GroupController</code> hierarchy based on the procedure group
	 * of the group context.<br>
	 * If a procedure group has a child then the <code>GroupController</code> is
	 * linked to the <code>GroupController</code> of the child group.<br>
	 * The procedure of the bottom level is linked to the InputController of the
	 * merge procedure.
	 * 
	 * @param aGroup the reference to the input group
	 * @return the top level group controller
	 */
	private GroupController buildGroupController(Group aGroup) {
		GroupController l_ret = null;
		Runnable l_next = inputCtl;
		List<String> l_list = context().groupNames();

		for (int i = l_list.size() - 1; i >= 0; i--) {
			String l_name = l_list.get(i);
			l_ret = new GroupController(l_name);

			l_ret.setProcedureGroup(procedureGroup, clMgr);
			l_ret.setInputGroup(aGroup);
			l_ret.setNextController(l_next);
		}
		return l_ret;
	}
}
