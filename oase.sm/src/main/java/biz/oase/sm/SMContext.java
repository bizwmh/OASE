/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm;

import java.util.List;

import biz.car.config.Configurable;

/**
 * The collection of runtime objects and parameters for an SM procedure.
 *
 * @version 2.0.0 28.10.2025 09:54:32
 */
public interface SMContext extends Configurable {

	/**
	 * @return the list of input channel names
	 */
	public List<String> inputNames();

	/**
	 * Looks up an input channel
	 * 
	 * @param aName the name of the channel
	 * @return the input channel found or <code>null</code>
	 */
	SMInput getInput(String aName);

	/**
	 * Looks up an output channel
	 * 
	 * @param aName the name of the channel
	 * @return the output channel found or <code>null</code>
	 */
	SMOutput getOutput(String l_out);

	/**
	 * @return the list of group names
	 */
	List<String> groupNames();
}
