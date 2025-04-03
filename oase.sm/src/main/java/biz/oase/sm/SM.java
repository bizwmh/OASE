/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm;

import biz.oase.car.OASE;
import biz.oase.sm.bundle.VAR;

/**
 * Key and value constants related to the SM service.
 * 
 * @version 1.0.0 08.03.2025 14:19:55
 */
public interface SM extends OASE {

	String DESCENDING = VAR.DESCENDING;
	String FORMAT = VAR.FORMAT;
	String GROUP = VAR.GROUP;;
	String HEADER = VAR.HEADER;
	String MERGE = VAR.MERGE;
	String ON_EXIT = VAR.ON_EXIT;
	String ON_INIT = VAR.ON_INIT;
	String ON_SELECTED = VAR.ON_SELECTED;
	String SORT = VAR.SORT;
	String TYPE = VAR.TYPE;
	String UNIQUE_KEY = VAR.UNIQUE_KEY;
}
