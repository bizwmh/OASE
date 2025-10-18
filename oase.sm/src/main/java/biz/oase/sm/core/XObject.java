/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.core;

import java.util.Properties;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigValue;
import com.typesafe.config.ConfigValueType;

import biz.car.config.ConfigAdapter;
import biz.oase.sm.SMObject;
import biz.oase.sm.context.ProcedureContext;

/**
 * Base class for objects being part of a SORT/MERGE procedure.<br>
 * The basic features provided by this class are:
 * <ul>
 * <li>name
 * <li>configuration parameters 
 * <li>runtime properties
 * </ul>
 * A <code>XObject</code> is part of a SM configuration. The configuration
 * entries starting with the name of this <code>XObject</code> are used to
 * build the runtime options for this object. The properties configure the state
 * of an <code>XObject</code> which impacts the behaviour during the execution
 * of a SORT/MERGE procedure.<br>
 * <p>
 *
 * @version 1.0.0 08.03.2025 14:35:57
 */
public class XObject
		extends ConfigAdapter
		implements SMObject {

	private ProcedureContext myContext;
	private Properties props;

	/**
	 * Creates a <code>XObject</code> instance with the given name.
	 * 
	 * @param aName the name for this object
	 */
	public XObject(String aName) {
		super(aName);

		props = new Properties();
	}

	/**
	 * @return the current procedure context
	 */
	public ProcedureContext context() {
		return myContext;
	}

	/**
	 * Releases all allocated in memory resources. As a result all properties will
	 * be removed from this SE object. When this method has finished this SE object
	 * instance shall no longer be usable.
	 */
	public void dispose() {
		Properties l_props = props();

		if (l_props != null) {
			l_props.clear();
		}
	}

	/**
	 * @return the property map of this SE object.
	 */
	public Properties props() {
		return props;
	}

	@Override
	public String toString() {
		String l_ret = getClass().getSimpleName();
		l_ret = l_ret + "." + getName(); //$NON-NLS-1$

		return l_ret;
	}

	/**
	 * TODO visit
	 * 
	 * @param aContext
	 */
	public void visit(ProcedureContext aContext) {
		myContext = aContext;
		Config l_conf = aContext.config();

		if (l_conf.hasPath(getName())) {
			ConfigValue l_value = l_conf.getValue(getName());

			if (l_value.valueType() == ConfigValueType.OBJECT) {
				l_conf = l_conf.getConfig(getName());

				accept(l_conf);
			}
		}
	}
}
