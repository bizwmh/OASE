/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.merge;

import java.util.ArrayList;
import java.util.List;

import biz.oase.sm.core.Group;
import biz.oase.sm.core.SM;
import biz.oase.sm.core.context.ProcedureContext;

/**
 * Manages the input processors of the merge procedure.
 *
 * @version 1.0.0 08.03.2025 15:05:56
 */
public class InputController implements SM, Runnable {

	private Group inputGroup;
	private List<InputProcessor> ipList;
	private int selected;

	/**
	 * Creates a default <code>InputController</code> instance.
	 */
	public InputController() {
		super();
	}

	/**
	 * Clears the internal list of input processors.
	 */
	public void dispose() {
		if (ipList != null) {
			ipList.clear();
			inputGroup.dispose();

			ipList = null;
			inputGroup = null;
		}
	}

	/**
	 * Creates the input group with the values from the input channel to process
	 * first.
	 * 
	 * @return the initialized input group
	 */
	public Group firstInputGroup() {
		ipList.forEach(p -> p.readNextRecord());
		// select first input
		selected = nextGroup();
		InputProcessor l_proc = ipList.get(selected);
		Group l_group = l_proc.group();
		// initialize input group
		inputGroup.copyOf(l_group);

		return inputGroup;
	}

	/**
	 * Processes all input channels with records belonging to the current input
	 * group.
	 * 
	 * @return the new value of the input group
	 */
	@Override
	public void run() {
		InputProcessor l_proc = ipList.get(selected);
		Group l_group = null;

		do {
			l_proc.run();

			selected = nextGroup();
			l_proc = ipList.get(selected);
			l_group = l_proc.group();
		} while (l_group.compareTo(inputGroup) == 0);
		inputGroup.copyOf(l_group);
	}

	/**
	 * Creates all input processors.
	 * 
	 * @param aContext the current porcedure context
	 */
	public void visit(ProcedureContext aContext) {
		ipList = new ArrayList<InputProcessor>();
		inputGroup = aContext.newGroup();
		selected = 0;

		aContext.inputNames()
				.forEach(name -> {
					InputProcessor l_ip = new InputProcessor(name);

					l_ip.visit(aContext);
					ipList.add(l_ip);
				});
	}

	private int nextGroup() {
		int l_ret = 0;
		Group l_next = ipList.get(0).group();

		for (int i = 1; i < ipList.size(); i++) {
			Group l_group = ipList.get(i).group();
			int l_comp = l_group.compareTo(l_next);

			if (l_comp < 0) {
				l_next = l_group;
				l_ret = i;
			}
		}
		return l_ret;
	}
}