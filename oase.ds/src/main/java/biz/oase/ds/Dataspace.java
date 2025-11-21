/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Dataspace Interface
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved.
 * -------------------------------------------------------------------------- */

package biz.oase.ds;

import biz.car.util.Identifiable;
import biz.car.util.ObjectRegistry;

/**
 * Represents a collection of application data objects.
 *
 * @version 2.0.0 18.11.2025 14:11:12
 */
public interface Dataspace extends Identifiable {
	
	ObjectRegistry<Dataspace> Registry = new ObjectRegistry<Dataspace>();
//
//	/**
//	 * TODO getTable
//	 * 
//	 * @param aName
//	 * @param aConfig
//	 * @return
//	 */
//	static Optional<DSTable> getTable(String aName, Config aConfig) {
//		Dataspace l_ds = null;
//		DSTable l_ret = null;
//		String l_dsid = null;
//		String l_in = aConfig.getString(INPUT);
//		Config l_conf = aConfig.getConfig(l_in);
//
//		if (l_conf.hasPath(DSID)) {
//			l_dsid = l_conf.getString(DSID);
//			l_ds = DSRegistry.get(l_dsid);
//		} else {
//			// derive the dsid from the extension of the file given by the PATH key
//			String l_path = l_conf.getString(PATH);
//			int l_ind = l_path.lastIndexOf('.');
//
//			if (l_ind > 0) {
//				l_dsid = l_path.substring(l_ind + 1).toUpperCase();
//				l_ds = DSRegistry.get(l_dsid);
//			}
//		}
//		if (l_ds == null) {
//			if (aConfig.hasPath(DSID)) {
//				l_dsid = aConfig.getString(DSID);
//				l_ds = DSRegistry.get(l_dsid);
//			}
//		}
//		if (l_ds != null) {
//			l_ret = l_ds.getTable(aName);
//		}
//		return Optional.ofNullable(l_ret);
//	}

	/**
	 * Supplies access to a data space cursor.
	 * 
	 * @param aName the unique name of the cursor
	 * @return a reference to a <code>DSCursor</code> instance.
	 */
	DSCursor getCursor(String aName);
}
