/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.core.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import biz.oase.sm.bundle.MSG;
import biz.oase.sm.core.Channel;
import biz.oase.sm.core.Input;
import biz.oase.sm.core.Output;
import biz.oase.sm.core.SM;

/**
 * A container for input or output channels of an SM procedure.
 *
 * @version 2.0.0 20.10.2025 14:03:30
 */
public class ChannelList<T extends Channel> implements SM {

	private static Map<String, Function<String, Channel>> newMap;

	static {
		newMap = new HashMap<>();

		newMap.put(INPUT, Input::new);
		newMap.put(OUTPUT, Output::new);
	}

	private List<String> channelNames;
	private String ioType;
	private Map<String, T> myMap;

	/**
	 * Creates a default <code>ChannelList</code> instance.
	 */
	public ChannelList(String aType) {
		super();

		ioType = aType;
		channelNames = new ArrayList<String>();
		myMap = new HashMap<String, T>();
	}

	/**
	 * @return the list of channel names
	 */
	public List<String> channelNames() {
		return channelNames;
	}

	/**
	 * Releases all allocated resources.
	 */
	public void dispose() {
		if (channelNames != null) {
			myMap.values().forEach(Channel::dispose);
			myMap.clear();

			myMap = null;
			channelNames = null;
		}
	}

	/**
	 * Getter for a channel.
	 * 
	 * @param aName the name of the channel
	 * @return the channel instance or <code>null</code>
	 */
	public T get(String aName) {
		return myMap.get(aName);
	}

	/**
	 * @return the number of channels.
	 */
	public int size() {
		return myMap.size();
	}

	/**
	 * Assigns the associated <code>ProcedureContext</code> to this channel list.
	 * 
	 * @param aContext the underlying procedure context
	 */
	@SuppressWarnings("unchecked")
	public void visit(ProcedureContext aContext) {
		channelNames = List.copyOf(aContext.asStringList(ioType));

		channelNames.forEach(n -> {
			if (!myMap.containsKey(n)) {
				if (aContext.hasPath(n)) {
					Channel l_channel = newMap.get(ioType).apply(n);

					myMap.put(n, (T) l_channel);
					l_channel.visit(aContext);
				} else {
					throw aContext.exception(MSG.CHANNEL_ENTRY_MISSING, n, ioType);
				}
			} else {
				throw aContext.exception(MSG.DUPLICATE_CHANNEL_ENTRY, ioType, n);
			}
		});
	}
}
