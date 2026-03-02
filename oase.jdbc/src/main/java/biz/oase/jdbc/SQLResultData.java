/* --------------------------------------------------------------------------
 * Project: OASE JDBC
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.jdbc;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Represents the collection of rows of a SQL result set.
 *
 * @version 2.0.0 02.03.2026 14:48:45
 */
public interface SQLResultData extends Iterator<Map<String, Object>>{
	
	/**
	 * @return the list of label names for a single row
	 */
	List<String> labels();
}
