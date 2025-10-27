/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.core.context;

import java.util.List;
import java.util.Objects;

import biz.oase.sm.core.Group;
import biz.oase.sm.core.SM;

/**
 * Functions related to the groups of a SM procedure.
 *
 * @version 2.0.0 20.10.2025 16:24:38
 */
public class GroupContext implements SM {

	private ProcedureContext ctx;
	private List<String> groupNames;

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
	}

	/**
	 * @return the list of group names
	 */
	public List<String> groupNames() {
		return groupNames;
	}

	/**
	 * @return a new <code>Group</code> instance for the group names of the
	 *         underlying SM procedure
	 */
	public Group newGroup() {
		Group l_ret = new Group(groupNames.get(0));

		for (int i = 1; i < groupNames.size(); i++) {
			l_ret = new Group(groupNames.get(i), l_ret);
			
			l_ret.visit(ctx);
		}
		return l_ret;
	}

	/**
	 * TODO visit
	 * 
	 * @param aContext
	 */
	void visit(ProcedureContext aContext) {
		ctx = Objects.requireNonNull(aContext);
		List<String> l_list = aContext.asStringList(GROUP);

		if (l_list.size() == 0) {
			l_list.add(Group.DUMMY);
		}
		groupNames = List.copyOf(l_list);
	}
}
