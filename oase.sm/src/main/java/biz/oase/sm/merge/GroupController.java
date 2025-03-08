/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.merge;

import java.util.List;

import biz.oase.sm.SM;
import biz.oase.sm.SMClient;
import biz.oase.sm.context.ProcedureContext;
import biz.oase.sm.core.Group;

/**
 * The <code>GroupController</code> controls the processing of all the input
 * records of one group. In a SM merger with a hierarchy of multiple groups
 * (i.e. several group levels) there is one <code>GroupController</code> per
 * group level.
 * <p>
 * The processing of a data group consists of:
 * <ul>
 * <li>The data group is initialized.<br>
 * This processing is executed once before any input record of that data group
 * is processed.
 * <li>All input records of the data group are processed.<br>
 * If the data group has a child group this processing is delegated to the
 * <code>GroupController</code> of the child group.<br>
 * If the data group does not have a child group this processing is delegated to
 * the <code>InputController</code> of the merge procedure.
 * <li>The data group is finalized. This processing is executed once after all
 * input records of that data group have been processed.
 * </ul>
 *
 * @version 1.0.0 08.03.2025 15:06:25
 */
public class GroupController implements SM, Runnable {

	/**
	 * Creates a <code>GroupController</code> hierarchy based on the procedure group
	 * of the group context.<br>
	 * If a procedure group has a child then the <code>GroupController</code> is
	 * linked to the <code>GroupController</code> of the child group.<br>
	 * The procedure of the bottom level is linked to the InputController of the
	 * merge procedure.
	 * 
	 * @param aPG         the procedure group
	 * @param aController a reference to the InputController of the merge procedure
	 * @return the GroupController for the top group, i.e. the group for level 0.
	 */
	public static GroupController build(Group aPG,
			InputController aController) {
		GroupController l_ret = null;
		Runnable l_next = aController;
		Group l_sig = aController.selectedInputGroup();
		ProcedureContext l_ctx = aPG.context();
		List<String> l_list = l_ctx.groupNames();

		for (int i = l_list.size() - 1; i >= 0; i--) {
			String l_name = l_list.get(i);
			l_ret = new GroupController();
			l_ret.pg = aPG.getGroup(l_name);
			l_ret.sig = l_sig.getGroup(l_name);
			l_ret.next = l_next;
			l_next = l_ret;

			if (l_ret.pg.hasPath(ON_EXIT)) {
				l_ret.exitClient = l_ctx.client(l_ret.pg.getString(ON_EXIT));
			}
			if (l_ret.pg.hasPath(ON_INIT)) {
				l_ret.exitClient = l_ctx.client(l_ret.pg.getString(ON_INIT));
			}
		}
		return l_ret;
	}

	private SMClient exitClient; // performs the group exit processing
	private SMClient initClient; // performs the group init processing
	private Runnable next; // The nex GroupController or the InputController
	private Group pg; // the group which is controlled by this controller
	private Group sig; // the selected input group

	/**
	 * Creates a <code>GroupController</code> instance.
	 */
	public GroupController() {
		super();
	}

	/**
	 * Invokes
	 * <ul>
	 * <li><code>init</code>
	 * <li><code>main</code>
	 * <li><code>exit</code>
	 * <li><code>select</code>
	 * </ul>
	 * in that order.
	 */
	@Override
	public void run() {
		init();
		main();
		exit();
		select();
	}

	/**
	 * Finalizes the group processing.<br>
	 * This method is invoked once after the completion of the <b>main</b> function.
	 */
	protected void exit() {
		if (exitClient != null) {
			exitClient.onExitGroup(pg);
		}
	}

	/**
	 * Initializes the group processing.<br>
	 * This method is invoked once before the <code>main</code> function.
	 */
	protected void init() {
		if (initClient != null) {
			initClient.onInitGroup(pg);
		}
	}

	/**
	 * Performs the main operations defined for this group.
	 */
	protected void main() {
		while (pg.compareTo(sig) == 0) {
			next.run(); // sig is updated by the input controller
		}
	}

	/**
	 * Select the group to be processed next.
	 */
	protected void select() {
		pg.valueOf(sig);
	}
}
