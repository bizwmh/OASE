/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm;

import biz.oase.framework.SEObject;

/**
 * A Sort/Merge data group.<br>
 * A group is defined by one or multiple ordered fields of an input record.<br>
 * As such a group defines a hierarchy of fields.
 * <p>
 * A <code>Group</code> instance exists for each field in the group.<br>
 * A record belongs to the group if the field values of the record match the
 * values of this group and all its parent groups.
 *
 * @version 2.0.0 28.10.2025 09:25:55
 */
public interface SMGroup extends SEObject {

	/**
	 * @return the current procedure context
	 */
	SMContext context();
}
