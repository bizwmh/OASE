/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Dataspace Gateway
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds;

import java.util.function.Supplier;

import biz.car.io.DataRecord;

/**
 * An iterator over the result set of a data space query.
 *
 * @version 1.0.0 23.03.2025 12:22:27
 */
public interface DSCursor extends Supplier<DataRecord> {

	/**
	 * Releases all allocated resources.
	 */
	void dispose();
	
	/**
	 * @return <code>true</code> if the cursor can provide a <code>DSRecord</code>.
	 */
	boolean next();
}
