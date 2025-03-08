/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.merge;

import java.util.ArrayList;
import java.util.List;

import biz.oase.sm.SM;
import biz.oase.sm.context.ProcedureContext;
import biz.oase.sm.core.Group;

/**
 * Manages the input processors of the merge procedure.
 *
 * @version 1.0.0 08.03.2025 15:05:56
 */
public class InputController implements SM, Runnable {

	private List<InputProcessor> icList;
	private int selected;
	private Group sig;

	/**
	 * Creates a default <code>InputController</code> instance.
	 */
	public InputController() {
		super();
		
		icList = new ArrayList<InputProcessor>();
	}

	/**
	 * Clears the internal list of input processors.
	 */
	public void dispose() {
		if (icList != null) {
			icList.clear();
			
			icList = null;
		}
	}

	/**
	 * @return the list of input processors
	 */
	public List<InputProcessor> list() {
		return List.copyOf(icList);
	}

	@Override
	public void run() {
		InputProcessor l_proc = icList.get(selected);

		l_proc.run();
		select();
	}

	/**
	 * Selects the next data group.
	 */
	public void select() {
		selected = 0;
		Group l_selected = icList.get(0).group();

		for (int i = 1; i < icList.size(); i++) {
			Group l_group = icList.get(i).group();
			int l_comp = l_group.compareTo(l_selected);

			if (l_comp < 0) {
				l_selected = l_group;
				selected = i;
			}
		}
		sig.copyOf(l_selected);
	}

	/**
	 * @return the selected input group
	 */
	public Group selectedInputGroup() {
		return sig;
	}

	/**
	 * Creates all input processors.
	 * 
	 * @param aContext the current porcedure context
	 */
	public void visit(ProcedureContext aContext) {
		sig = aContext.newGroup();
		
		aContext.inputNames()
				.forEach(name -> {
					InputProcessor l_ip = new InputProcessor(name);
					
					l_ip.visit(aContext);
					icList.add(l_ip);
				});
	}
}
