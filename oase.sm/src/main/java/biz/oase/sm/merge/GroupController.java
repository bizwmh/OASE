/* --------------------------------------------------------------------------
 * Project: XXX
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.merge;

import biz.oase.sm.core.Group;
import biz.oase.sm.core.SM;

/**
 * TODO GroupController comment
 *
 * @version 1.0.0 23.10.2025 11:53:07
 */
public class GroupController implements SM, Runnable {

//	private SMClient exitClient; // performs the group exit processing
//	private SMClient initClient; // performs the group init processing
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
//		if (exitClient != null) {
//			exitClient.onExitGroup(pg);
//		}
	}

	/**
	 * Initializes the group processing.<br>
	 * This method is invoked once before the <code>main</code> function.
	 */
	protected void init() {
//		if (initClient != null) {
//			initClient.onInitGroup(pg);
//		}
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
	void setInputgGroup(Group aGroup) {
		inputGroup = aGroup.getGroup(name);
	}

	/**
	 * @param aController the next controller to set
	 */
	void setNextController(Runnable aController) {
		nextController = aController;
	}

	/**
	 * @param aGroup the procedure group to set
	 */
	void setProcedureGroup(Group aGroup) {
		procedureGroup = aGroup.getGroup(name);
	}
}
