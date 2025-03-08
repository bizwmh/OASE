/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The <code>SMConfig</code> holds the runtime parameters of an
 * <code>SMClient</code>.<br>
 * Runtime parameters are structured into 4 groups:
 * <ul>
 * <li>procedure parameters<br>
 * These parameters are stored as regular key-value pairs
 * <li>group parameters<br>
 * With key SM.GROUP the procedure parameters contain a list of all group
 * names.<br>
 * For each group name the associated parameters are stored in a separate
 * map.<br>
 * The group name is the key of the group map.
 * <li>input parameters<br>
 * With key SM.INPUT the procedure parameters contain a list of all input
 * channel names.<br>
 * For each channel name the associated parameters are stored in a separate
 * map.<br>
 * The channel name is the key of the input channel map.
 * <li>output parameters<br>
 * With key SM.OUTPUT the procedure parameters contain a list of all output
 * channel names.<br>
 * For each channel name the associated parameters are stored in a separate
 * map.<br>
 * The channel name is the key of the output channel map.
 * <p>
 * When the <code>SMClient</code> is initialized for each group or channel name
 * a type corresponding <code>XObject</code> is created and the associated map
 * from this <code>SMConfig</code> is copied into the created
 * <code>XObject</code>.
 *
 * @version 1.0.0 13.11.2024 13:06:24
 */
public class SMConfig
		extends HashMap<String, Object>
		implements SM {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a <code>SMConfig</code> instance.
	 * 
	 * @param aName the name for the <code>SMClient</code>
	 */
	public SMConfig(String aName) {
		super();

		put(NAME, aName);
	}

	/**
	 * Defines the fields with descending sort order.
	 * 
	 * @param aName
	 */
	public void descending(List<String> aName) {
		aName.forEach(name -> getMap(name).put(DESCENDING, true));
	}

	/**
	 * Defines the procedure to execute.<br>
	 * Valid values are SM.MERGE or SM.SORT.
	 * 
	 * @param aValue the identifier for the procedure
	 */
	public void exec(String aValue) {
		put(EXEC, aValue);
	}

	/**
	 * Gets the object map for the given name.
	 * 
	 * @param aName the name of the group or channel.
	 * @return the object map.<br>
	 *         If the map is not yet existing an empty map will be created.
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object> getMap(String aName) {
		Object l_ret = get(aName);

		if (l_ret == null) {
			l_ret = new HashMap<String, Object>();

			put(aName, l_ret);
		}
		return (Map<String, Object>) l_ret;
	}

	/**
	 * Defines the key fields building a group.
	 * 
	 * @param aValue the list of field names
	 */
	public void group(List<String> aValue) {
		put(GROUP, aValue);
	}

	/**
	 * Provides the values for the CSV header.
	 * 
	 * @param aName  the name of the channel
	 * @param aValue the list of header values
	 */
	public void header(String aName, List<String> aValue) {
		getMap(aName).put(HEADER, aValue);
	}

	/**
	 * Defines the names of the input channels.
	 * 
	 * @param aValue the list of names
	 */
	public void input(List<String> aValue) {
		put(INPUT, aValue);
	}

	/**
	 * Defines the key for a group consumer when the group is entered.
	 * 
	 * @param aName  the name of the group
	 * @param aValue the key of the group consumer
	 */
	public void onExit(String aName, String aValue) {
		getMap(aName).put(ON_EXIT, aValue);
	}

	/**
	 * Defines the key for a group consumer when the group is exited.
	 * 
	 * @param aName  the name of the group
	 * @param aValue the key of the group consumer
	 */
	public void onInit(String aName, String aValue) {
		getMap(aName).put(ON_INIT, aValue);
	}

	/**
	 * Defines the key for an input consumer.
	 * 
	 * @param aName  the name of the input channel
	 * @param aValue the key of the input consumer
	 */
	public void onSelected(String aName, String aValue) {
		getMap(aName).put(ON_SELECTED, aValue);
	}

	/**
	 * Defines the names of the output channels.
	 * 
	 * @param aValue the list of names
	 */
	public void output(List<String> aValue) {
		put(OUTPUT, aValue);
	}

	/**
	 * Assigns an output channel to an input channel.
	 * 
	 * @param aName  the name of the input channel
	 * @param aValue the name of the output channel
	 */
	public void output(String aName, String aValue) {
		getMap(aName).put(OUTPUT, aValue);
	}

	/**
	 * Defines the path to the file in the file system.
	 * 
	 * @param aName  the name of the channel
	 * @param aValue the value of the path
	 */
	public void path(String aName, String aValue) {
		getMap(aName).put(PATH, aValue);
	}

	/**
	 * General method to store object parameters.
	 * 
	 * @param aName  the name of the object
	 * @param aKey   the parameter key
	 * @param aValue the parameter value
	 */
	public void put(String aName, String aKey, Object aValue) {
		getMap(aName).put(aKey, aValue);
	}

	/**
	 * Defines the group fields where the group value just occurs once.
	 * 
	 * @param aName the list of group names
	 */
	public void uniqueKey(List<String> aName) {
		aName.forEach(name -> getMap(name).put(UNIQUE_KEY, true));
	}
}
