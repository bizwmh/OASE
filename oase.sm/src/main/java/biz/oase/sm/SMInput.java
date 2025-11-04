/* --------------------------------------------------------------------------
 * Project: XXX
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm;

import biz.car.csv.CSVRecord;
import biz.oase.framework.SEObject;

/**
 * TODO SMInput comment
 *
 * @version 1.0.0 28.10.2025 09:29:41
 */
public interface SMInput extends SEObject {

	/**
	 * TODO getCurrent
	 * @return
	 */
	CSVRecord getCurrent();

	/**
	 * TODO context
	 * @return
	 */
	SMContext context();

}
