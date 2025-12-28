/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Dataspace Interface
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds.core;

import java.io.File;
import java.util.function.UnaryOperator;

import com.typesafe.config.Config;

import biz.car.XRunnable;
import biz.car.config.ConfigAdapter;
import biz.car.csv.CSVMapper;
import biz.car.csv.CSVRecord;
import biz.car.io.FSObject;
import biz.oase.ds.DS;
import biz.oase.ds.DSAgent;
import biz.oase.ds.Dataspace;
import biz.oase.ds.bundle.MSG;

/**
 * Creates an export file of data entries in a data space.
 *
 * @version 2.0.0 24.11.2025 13:45:41
 */
public abstract class DSClient extends ConfigAdapter
		implements DS, XRunnable {

	protected DSAgent myAgent;
	protected UnaryOperator<CSVRecord> myMapper;

	/**
	 * Creates a default <code>Export</code> instance.
	 */
	public DSClient() {
		super();
		
		myMapper = rec -> rec;
	}

	@Override
	public void accept(Config aConfig) {
		super.accept(aConfig);
		
		String l_agentName = getString(INPUT);
		Config l_conf = config();
		l_conf = l_conf.getConfig(l_agentName);
		String l_dsid = getString(DSID, null);
		
		if (l_dsid == null) {
			String l_path = l_conf.getString(PATH);
			File l_file = new File(l_path);
			FSObject l_fso = () -> l_file;
			l_dsid = l_fso.getType().toUpperCase();
		}
		Dataspace l_ds = Dataspace.Registry.get(l_dsid);
		
		if (l_ds == null) {
			throw exception(MSG.DS_NOT_FOUND, l_dsid);
		}
		myAgent = l_ds.getAgent(l_agentName);
		
		myAgent.accept(aConfig);

		String l_mapping = myAgent.getString(MAPPING, null);
		
		if (l_mapping != null) {
			myMapper = new CSVMapper();
			
			((CSVMapper) myMapper).load(l_mapping);
		}
		myAgent.openConnection();
	}

	@Override
	public void dispose() {
		// Dispose agent
		if (myAgent != null) {
			myAgent.dispose();

			myAgent = null;
		}
	}
}
