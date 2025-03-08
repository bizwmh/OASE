/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import biz.oase.sm.SM;
import biz.oase.sm.core.Group;

/**
 * Group related functions and objects for the SM procedure.
 *
 * @version 1.0.0 08.03.2025 14:46:19
 */
public class GroupContext implements SM {

	private List<String> groupNames;
	private Map<String, Group> inputGroups;
	private Group pg; // the procedure group

	/**
	 * Creates a default <code>GroupContext</code> instance.
	 */
	public GroupContext() {
		super();
	}

	/**
	 * Releases all allocated resources.
	 */
	public void dispose() {
		groupNames = null;

		if (pg != null) {
			pg.dispose();

			pg = null;
		}
		if (inputGroups != null) {
			inputGroups.values().forEach(Group::dispose);

			inputGroups = null;
		}
	}

	/**
	 * @return the list of group names
	 */
	public List<String> groupNames() {
		return groupNames;
	}

	/**
	 * @param aName the name of the input channel
	 * @return a reference to the input group
	 */
	public Group inputGroup(String aName) {
		return inputGroups.get(aName);
	}

	/**
	 * @return a new <code>Group</code> instance for the group names of the
	 *         underlying SM procedure
	 */
	public Group newGroup() {
		Group l_ret = new Group(groupNames.get(0));

		for (int i = 1; i < groupNames.size(); i++) {
			l_ret = new Group(groupNames.get(i), l_ret);
		}
		return l_ret;
	}

	/**
	 * @return a reference to the procedure group
	 */
	public Group procedureGroup() {
		return pg;
	}

	/**
	 * TODO visit
	 * 
	 * @param aContext
	 */
	void visit(ProcedureContext aContext) {
		List<String> l_list = aContext.asStringList(GROUP);

		if (l_list.size() == 0) {
			l_list.add(Group.DUMMY);
		}
		groupNames = List.copyOf(l_list);
		pg = newGroup();
		inputGroups = new HashMap<String, Group>();

		pg.visit(aContext);
		aContext.inputNames()
				.forEach(name -> {
					Group l_group = newGroup();

					l_group.visit(aContext);
					inputGroups.put(name, l_group);
				});
	}
}
