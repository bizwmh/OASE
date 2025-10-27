/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.merge;

import biz.oase.sm.core.Group;
import biz.oase.sm.core.SM;
import biz.oase.sm.core.context.ProcedureContext;

/**
 * Controls the processing of a data group within a merge procedure.
 *
 * @version 2.0.0 21.10.2025 18:57:21
 */
public interface MergeController extends SM {

	/**
	 * Performs the steps to execute the processing of a data group.
	 * 
	 * @param aGroup the data group to process
	 * @return the data group to process next
	 */
	Group control(Group aGroup);
	
	/**
	 * Releases all allocated resourced.
	 */
	void dispose();
	
	/**
	 * Initializes the controller from the procedure context
	 * 
	 * @param aContext the underlying context of the merge procedure
	 */
	void visit(ProcedureContext aContext);
}
