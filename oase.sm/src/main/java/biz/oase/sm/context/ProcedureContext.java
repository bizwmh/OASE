/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.context;

import java.util.List;

import com.typesafe.config.Config;

import biz.car.config.ConfigObject;
import biz.car.util.ClassUtil;
import biz.car.util.ObjectRegistry;
import biz.oase.sm.SM;
import biz.oase.sm.SMClient;
import biz.oase.sm.core.Group;
import biz.oase.sm.core.Input;
import biz.oase.sm.core.Output;

/**
 * The collection of runtime objects and parameters for an SM procedure.
 *
 * @version 1.0.0 08.03.2025 14:45:53
 */
public class ProcedureContext extends ConfigObject implements SM {

	private ObjectRegistry<SMClient> CR = new ObjectRegistry<SMClient>();
	private GroupContext groupCtx;
	private ChannelContext inputChannels;
	private ChannelContext outputChannels;

	/**
	 * Creates a default <code>ProcedureContext</code> instance.
	 */
	public ProcedureContext() {
		super();
	}

	@Override
	public void accept(Config aConfig) {
		super.accept(aConfig);

		inputChannels = new ChannelContext(INPUT);
		outputChannels = new ChannelContext(OUTPUT);
		groupCtx = new GroupContext();

		inputChannels.visit(this);
		outputChannels.visit(this);
		groupCtx.visit(this);
	}

	/**
	 * TODO register
	 * 
	 * @param string
	 * @return
	 */
	public SMClient client(String anId) {
		SMClient l_ret = CR.get(anId);

		if (l_ret == null) {
			Class<?> l_class = ClassUtil.Registry.get(anId);
			l_ret = ClassUtil.newInstance(l_class);

			CR.register(anId, l_ret);
		}
		return l_ret;
	}

	/**
	 * Releases all allocated in memory resources.<br>
	 * When this method has finished this task instance shall no longer be usable.
	 */
	public void dispose() {
		if (outputChannels != null) {
			outputChannels.dispose();

			outputChannels = null;
		}
		if (inputChannels != null) {
			inputChannels.dispose();

			inputChannels = null;
		}
		if (groupCtx != null) {
			groupCtx.dispose();

			groupCtx = null;
		}
	}

	/**
	 * Looks up an input channel
	 * 
	 * @param aName the name of the channel
	 * @return the input channel found or <code>null</code>
	 */
	public Input getInput(String aName) {
		return inputChannels.get(aName);
	}

	/**
	 * Looks up an output channel
	 * 
	 * @param aName the name of the channel
	 * @return the output channel found or <code>null</code>
	 */
	public Output getOutput(String aName) {
		return outputChannels.get(aName);
	}

	/**
	 * @return the list of group names
	 */
	public List<String> groupNames() {
		return groupCtx.groupNames();
	}

	/**
	 * @param aName the name of the input channel
	 * @return a reference to the input group
	 */
	public Group inputGroup(String aName) {
		return groupCtx.inputGroup(aName);
	}

	/**
	 * @return the list of input channel names
	 */
	public List<String> inputNames() {
		return inputChannels.channelNames();
	}

	/**
	 * @return a new <code>Group</code> instance
	 */
	public Group newGroup() {
		Group l_ret = groupCtx.newGroup();
		
		l_ret.visit(this);
		
		return l_ret;
	}

	/**
	 * @return the list of output channel names
	 */
	public List<String> outputNames() {
		return outputChannels.channelNames();
	}

	/**
	 * @return a reference to the procedure group
	 */
	public Group procedureGroup() {
		return groupCtx.procedureGroup();
	}
}
