/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.merge;

import biz.car.util.ObjectRegistry;
import biz.oase.sm.SMClient;

/**
 * Manages clients of the merge procedure which are called back by the merger in
 * order to handle group and input events.
 *
 * @version 1.0.0 30.10.2025 10:54:25
 */
public class ClientManager {

	private ObjectRegistry<SMClient> clientMap;

	/**
	 * Creates a default <code>ClientManager</code> instance.
	 */
	public ClientManager() {
		super();

		clientMap = new ObjectRegistry<SMClient>();
	}

	public SMClient getClient(String anId) {
		SMClient l_ret = clientMap.get(anId);
		
		if (l_ret == null) {
			l_ret = clientMap.forName(anId);
		}
		return l_ret;
	}
}
