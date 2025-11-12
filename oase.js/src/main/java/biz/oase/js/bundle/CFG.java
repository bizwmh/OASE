/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Job Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.js.bundle;

import java.util.List;

import org.osgi.service.cm.ManagedService;
import org.osgi.service.component.annotations.Component;

import biz.oase.framework.XManagedService;
import biz.oase.js.inbox.Inbox;

/**
 * Job Service Runtime Configuration.
 *
 * @version 2.0.0 08.11.2025 14:30:17
 */
@Component(
		property = { "service.pid=oase.js" },
		service = ManagedService.class, 
		immediate = true)
public class CFG extends XManagedService {

	public static List<String> EXECLIB;
	public static String INBOX;
	public static String OUTBOX;
	
	private Inbox in;
	
	/**
	 * Creates a default <code>BND</code> instance.
	 */
	public CFG() {
		super();
		
		in = new Inbox();
	}

	@Override
	protected void disabledService() {
		in.stop();
	}

	@Override
	protected void updatedService() {
		EXECLIB = List.copyOf(asStringList(VAR.EXECLIB));
		INBOX = getString(VAR.INBOX);
		OUTBOX = getString(VAR.OUTBOX);

		in.accept(config());
		in.start();
	}
}
