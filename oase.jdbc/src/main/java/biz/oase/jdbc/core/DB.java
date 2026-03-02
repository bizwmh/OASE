/* --------------------------------------------------------------------------
 * Project: OASE JDBC
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.jdbc.core;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.jdbc.DataSourceFactory;

import com.typesafe.config.Config;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import biz.car.SYS;
import biz.car.XRuntimeException;
import biz.car.config.ACS;
import biz.car.config.ConfigAdapter;
import biz.oase.jdbc.DataBase;
import biz.oase.jdbc.JDBC;
import biz.oase.jdbc.SQLStatement;
import biz.oase.jdbc.Table;
import biz.oase.jdbc.bundle.MSG;

/**
 * Client implementation of the <code>DataBase</code> interface.
 *
 * @version 2.0.0 27.02.2026 16:07:01
 */
public class DB extends ConfigAdapter implements DataBase, JDBC {

	private static BundleContext ctx; // injected by BND
	private static String DRV_CLS = DataSourceFactory.OSGI_JDBC_DRIVER_CLASS;

	private boolean autoCommit;
	private long connectionTimeoutMs;
	private DataSource dataSource;
	private String driverClass;
	private String jdbcUrl;
	private int maxPoolSize;
	private int minIdle;
	private String password;
	private String schema;
	private Map<String, DBTable> tableMap;
	private String username;

	/**
	 * Creates a default <code>DB</code> instance.
	 */
	public DB() {
		super();
	}

	@Override
	public void accept(Config aConfig) {
		super.accept(aConfig);

		try {
			ACS.initialize(this, config());

			// 1. Get DataSourceFactory from the OSGi Registry
			DataSourceFactory l_fac = dataSourceFactory();

			// 2. Create native (unpooled) DataSource from driver
			Properties l_props = new Properties();
			l_props.put(DataSourceFactory.JDBC_URL, jdbcUrl);
			l_props.put(DataSourceFactory.JDBC_USER, username);
			l_props.put(DataSourceFactory.JDBC_PASSWORD, password);

			DataSource l_nds = l_fac.createDataSource(l_props);

			// 3. Give native DataSource to HikariCP
			HikariConfig l_config = new HikariConfig();
			l_config.setDataSource(l_nds);
			l_config.setConnectionTimeout(connectionTimeoutMs);
			l_config.setMaximumPoolSize(maxPoolSize);
			l_config.setMinimumIdle(minIdle);
			l_config.setAutoCommit(autoCommit);

			dataSource = new HikariDataSource(l_config);
		} catch (XRuntimeException anEx) {
			throw anEx;
		} catch (Exception anEx) {
			throw SYS.LOG.exception(anEx);
		}

	}

	@Override
	public DataSource getDataSource() {
		return dataSource;
	}

	@Override
	public Table getTable(String aTable) {
		return getTableMap().get(aTable);
	}

	@Override
	public boolean hasTable(String aTable) {
		return getTableMap().containsKey(aTable);
	}

	@Override
	public SQLStatement sqlStatement() {
		return new SQLExecutor(this);
	}

	@Override
	public List<String> tableNames() {
		return getTableMap().keySet()
				.stream()
				.toList();
	}

	@SuppressWarnings("nls")
	private DataSourceFactory dataSourceFactory() throws InvalidSyntaxException {
		Collection<ServiceReference<DataSourceFactory>> l_refs;
		String l_filter = "(" + DRV_CLS + "=" + driverClass + ")";
		l_refs = ctx.getServiceReferences(DataSourceFactory.class, l_filter);
		if (l_refs == null || l_refs.isEmpty()) {
			throw SYS.LOG.exception(MSG.DSFACTORY_NOT_FOUND, driverClass);
		}
		DataSourceFactory l_ret = ctx.getService(l_refs.iterator().next());

		return l_ret;
	}

	private Map<String, DBTable> getTableMap() {
		if (tableMap == null) {
			tableMap = new HashMap<String, DBTable>();
			try (Connection l_conn = getDataSource().getConnection()) {
				DatabaseMetaData l_meta = l_conn.getMetaData();
				ResultSet l_rs = l_meta.getTables(
						null, // catalog (null = all)
						schema, // schema (null = all, z.B. "PUBLIC" für H2)
						"%", // tablename-pattern (% = all) //$NON-NLS-1$
						new String[] { TABLE } // no VIEW, SYSTEM TABLE etc.
				);
				while (l_rs.next()) {
					String l_tableName = l_rs.getString(TABLE_NAME);
					DBTable l_table = new DBTable(l_tableName, this);

					tableMap.put(l_tableName, l_table);
				}
			} catch (SQLException anEx) {
				throw SYS.LOG.exception(anEx);
			}
		}
		return tableMap;
	}
}
