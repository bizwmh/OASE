/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm;

import biz.car.CAR;
import biz.oase.car.OASE;
import biz.oase.sm.bundle.VAR;

/**
 * Key and value constants related to the SM service.
 * 
 * @version 2.0.0 30.03.2026 10:40:50
 */
public interface SM extends CAR {

	String DESCENDING = VAR.DESCENDING;
	String FORMAT = VAR.FORMAT;
	String GROUP = OASE.GROUP;
	String HEADER = VAR.HEADER;
	String HEADER_REF = VAR.HEADER_REF;
	String HIGH_VALUE = VAR.HIGH_VALUE;
	String LOW_VALUE = VAR.LOW_VALUE;
	String MERGE = VAR.MERGE;
	String ON_EXIT = VAR.ON_EXIT;
	String ON_INIT = VAR.ON_INIT;
	String ON_SELECTED = VAR.ON_SELECTED;
	String SORT = VAR.SORT;
	String TYPE = VAR.TYPE;
	String UNIQUE_KEY = VAR.UNIQUE_KEY;
}
