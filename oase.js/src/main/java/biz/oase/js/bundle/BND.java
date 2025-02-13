/* --------------------------------------------------------------------------
 * Project: OASE Job Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.js.bundle;

import java.util.List;

import org.osgi.service.cm.ManagedService;
import org.osgi.service.component.annotations.Component;

import biz.car.util.ClassUtil;
import biz.oase.framework.XManagedService;
import biz.oase.js.core.Inbox;

/**
 * Bundle Runtime Options.
 *
 * @version 1.0.0 11.02.2025 12:23:21
 */
@Component(
		property = { "service.pid=oase.js" },
		service = ManagedService.class, 
		immediate = true)
public class BND extends XManagedService {

	public static List<String> EXECLIB;
	
	private Inbox in;
	
	/**
	 * Creates a default <code>BND</code> instance.
	 */
	public BND() {
		super();
		
		in = new Inbox();
	}

	@Override
	protected void disabledService() {
		in.stop();
	}

	@Override
	protected void updatedService() {
		EXECLIB = asStringList(VAR.EXECLIB);
		Class<?> l_class = biz.car.util.Dummy.class;
		
		ClassUtil.Registry.register("extract", l_class);
		ClassUtil.Registry.register("sort", l_class);
		ClassUtil.Registry.register("merge", l_class);
		in.accept(config());
		in.start();
	}
}
