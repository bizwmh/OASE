/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Common Application Runtime
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.car;

import java.util.Properties;

import biz.car.config.Configurable;

/**
 * General purpose interface for service engine objects.<br>
 * The basic features defined by this interface are:
 * <ul>
 * <li>name
 * <li>configuration parameters 
 * <li>runtime properties
 * </ul>
 * <p>
 *
 * @version 2.0.0 01.02.2026 17:19:00
 */
public interface SEObject extends OASE, Configurable{

	/**
	 * @return the property map of this SE object.
	 */
	Properties props();
}
