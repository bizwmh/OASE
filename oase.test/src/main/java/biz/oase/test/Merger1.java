/* --------------------------------------------------------------------------
 * Project: XXX
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.test;

import java.io.File;

import org.osgi.service.component.annotations.Component;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import biz.oase.framework.XComponent;
import biz.oase.sm.merge.Merger;
import biz.oase.sm.sort.Sorter;

/**
 * TODO Merger1 comment
 *
 * @version 1.0.0 23.10.2025 16:16:36
 */
@Component(immediate = true)
public class Merger1 extends XComponent {

	/**
	 * Creates a default <code>Merger1</code> instance.
	 */
	public Merger1() {
		super();
	}

	@Override
	protected void doActivate() {
		info("Hier ist {}", getClass().getSimpleName());// TODO Auto-generated method stub
		
		Sorter l_sorter = new Sorter();
		File l_confFile = new File("workspace","Sorter1.conf");
		Config l_conf = ConfigFactory.parseFile(l_confFile);
		
		l_sorter.accept(l_conf);
		l_sorter.run();
		
		Merger l_merger = new Merger();
		l_confFile = new File("workspace","Merger1.conf");
		l_conf = ConfigFactory.parseFile(l_confFile);
		
		l_merger.accept(l_conf);
		l_merger.run();
		
		info("Hier war {}", getClass().getSimpleName());// TODO Auto-generated method stub
	}

	@Override
	protected void doDeactivate() {
		// TODO Auto-generated method stub

	}

}
