/* --------------------------------------------------------------------------
 * Project: OASE JDBC
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.jdbc;

import biz.oase.jdbc.core.DB;

/**
 * Factory for <code>DataBase</code> instances.
 *
 * @version 2.0.0 01.03.2026 09:11:47
 */
public interface DataBaseFactory {

	/**
	 * Creates a DataBase from the given configuration.
	 * 
	 * @param aConfig the underlying data source configuration
	 * @return the <code>DataBase</code> instance
	 */
	static DataBase apply(DataSourceConfig aConfig) {
		DB l_ret = new DB();

		l_ret.accept(aConfig.config());

		return l_ret;
	}
}
