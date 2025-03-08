/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm;

import java.util.Properties;

import biz.car.XLogger;
import biz.car.config.XConfig;

/**
 * Base interface for objects being part of a SORT/MERGE procedure.<br>
 * The basic features defined by this class are:
 * <ul>
 * <li>name
 * <li>configuration parameters 
 * <li>runtime properties
 * </ul>
 * A <code>SMObject</code> is part of a SM configuration. The configuration
 * entries starting with the name of this <code>SMObject</code> are used to
 * build the runtime options for this object. The properties configure the state
 * of an <code>SMObject</code> which impacts the behaviour during the execution
 * of a SORT/MERGE procedure.<br>
 * <p>
 *
 * @version 1.0.0 08.03.2025 14:24:59
 */
public interface SMObject
		extends XConfig, XLogger, SM {

	/**
	 * @return the name for this <code>Configurable</code>
	 */
	String getName();

	/**
	 * @return the property map of this SE object.
	 */
	Properties props();
}
