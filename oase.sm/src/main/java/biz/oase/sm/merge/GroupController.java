/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.merge;

import java.util.function.Consumer;

import biz.oase.sm.SMClient;
import biz.oase.sm.SMGroup;
import biz.oase.sm.core.Group;
import biz.oase.sm.core.SM;

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
 * @version 2.0.0 21.10.2025 17:18:05
 */
public class GroupController implements SM, Runnable {

	private static Consumer<SMGroup> nop = group -> {
	};

	private Consumer<SMGroup> exitConsumer;
	private Consumer<SMGroup> initConsumer;
	private Group inputGroup;
	private String name;
	private Runnable nextController;
	private Group procedureGroup;

	/**
	 * Creates a default <code>GroupController</code> instance.
	 */
	public GroupController(String aName) {
		super();

		name = aName;
		initConsumer = nop;
		exitConsumer = nop;
	}

	/**
	 * Releases all allocated resources
	 */
	public void dispose() {
		// nothing to do
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
		exitConsumer.accept(procedureGroup);
	}

	/**
	 * Initializes the group processing.<br>
	 * This method is invoked once before the <code>main</code> function.
	 */
	protected void init() {
		initConsumer.accept(procedureGroup);
	}

	/**
	 * Performs the main operations defined for this group.
	 */
	protected void main() {
		while (procedureGroup.compareTo(inputGroup) == 0) {
			nextController.run(); // input group is updated by the input controller
		}
	}

	/**
	 * Select the group to be processed next.
	 */
	protected void select() {
		procedureGroup.valueOf(inputGroup);
	}

	/**
	 * @param aGroup the input group to set
	 */
	void setInputGroup(Group aGroup) {
		inputGroup = aGroup.getGroup(name);
	}

	/**
	 * @param aController the next controller to set
	 */
	void setNextController(Runnable aController) {
		nextController = aController;
	}

	/**
	 * @param aGroup   the procedure group to set
	 * @param aManager the client manager used to set init and exit consumer of the
	 *                 procedure group
	 */
	void setProcedureGroup(Group aGroup, ClientManager aManager) {
		procedureGroup = aGroup.getGroup(name);
		String l_init = procedureGroup.getString(ON_INIT, null);
		String l_exit = procedureGroup.getString(ON_EXIT, null);

		if (l_init != null) {
			SMClient l_client = aManager.getClient(l_init);
			initConsumer = SMClient.ConsumerOnInit.apply(l_client);
		}
		if (l_exit != null) {
			SMClient l_client = aManager.getClient(l_exit);
			exitConsumer = SMClient.ConsumerOnExit.apply(l_client);
		}
	}
}
