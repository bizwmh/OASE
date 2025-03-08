/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.context;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import biz.oase.sm.SM;
import biz.oase.sm.bundle.MSG;
import biz.oase.sm.core.Channel;
import biz.oase.sm.core.Input;
import biz.oase.sm.core.Output;

/**
 * Container for a set of channels providing access to runtime operations of the
 * contained channels.<br>
 * A SM procedure has one context for the input channels and one context for the
 * output channels.
 *
 * @version 1.0.0 08.03.2025 14:46:40
 */
public class ChannelContext
		implements SM {

	private static Map<String, Function<String, Channel>> newMap;

	static {
		newMap = new HashMap<>();

		newMap.put(INPUT, Input::new);
		newMap.put(OUTPUT, Output::new);
	}

	private List<String> channelNames;
	private Map<String, Channel> myMap;
	private String type;

	/**
	 * Creates a default <code>ChannelContext</code> instance.
	 * 
	 * @param aType the type for a set of input or output channels
	 */
	public ChannelContext(String aType) {
		super();

		type = aType;
		myMap = new HashMap<>();
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
		if (myMap != null) {
			myMap.values().forEach(Channel::dispose);
			myMap.clear();

			myMap = null;
		}
		channelNames = null;
	}

	/**
	 * Getter for a channel.
	 * 
	 * @param aName the name of the channel
	 * @return the channel instance or <code>null</code>
	 */
	@SuppressWarnings("unchecked")
	public <T extends Channel> T get(String aName) {
		return (T) myMap.get(aName);
	}

	/**
	 * @return the number of channels.
	 */
	public int size() {
		return myMap.size();
	}

	/**
	 * TODO visit
	 * 
	 * @param aContext
	 */
	void visit(ProcedureContext aContext) {
		channelNames = List.copyOf(aContext.asStringList(type));

		channelNames.forEach(n -> {
			if (!myMap.containsKey(n)) {
				if (aContext.hasPath(n)) {
					Channel l_channel = newMap.get(type).apply(n);

					myMap.put(n, l_channel);
					l_channel.visit(aContext);
				} else {
					throw aContext.exception(MSG.CHANNEL_ENTRY_MISSING, n, type);
				}
			} else {
				throw aContext.exception(MSG.DUPLICATE_CHANNEL_ENTRY, type, n);
			}
		});
	}
}
