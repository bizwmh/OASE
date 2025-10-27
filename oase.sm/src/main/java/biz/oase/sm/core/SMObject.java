/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.core;

import java.util.Objects;
import java.util.Properties;

import org.slf4j.Logger;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigValue;
import com.typesafe.config.ConfigValueType;

import biz.car.config.ConfigAdapter;
import biz.oase.framework.SEObject;
import biz.oase.sm.core.context.ProcedureContext;

/**
 * Base class for objects being part of a SORT/MERGE procedure.<br>
 * The basic features provided by this class are:
 * <ul>
 * <li>name
 * <li>configuration parameters
 * <li>runtime properties
 * </ul>
 * A <code>SMObject</code> is part of a SM configuration. The configuration
 * entries starting with the name of this <code>SMObject</code> are used to
 * build the runtime options for this object. The properties configure the state
 * of an <code>SMObject</code> which impacts the behavior during the execution
 * of a SORT/MERGE procedure.<br>
 * <p>
 *
 * @version 2.0.0 19.10.2025 13:10:50
 */
public class SMObject
		extends ConfigAdapter
		implements SM, SEObject {

	private ProcedureContext myContext;
	private Properties props;

	/**
	 * Creates a <code>SMObject</code> instance with the given name.
	 * 
	 * @param aName the name for this object
	 */
	public SMObject(String aName) {
		super(aName);

		props = new Properties();
	}

	@Override
	public void accept(Config aConfig) {
		Objects.requireNonNull(myContext);
		
		Config l_conf = aConfig;

		if (aConfig.hasPath(getName())) {
			ConfigValue l_value = aConfig.getValue(getName());

			if (l_value.valueType() == ConfigValueType.OBJECT) {
				l_conf = aConfig.getConfig(getName());
			}
		}
		super.accept(l_conf);
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

	@Override
	public Logger logger() {
		return myContext.logger();
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
	 * Assigns the associated <code>ProcedureContext</code> to this object.<br>
	 * Invokes the <code>accept</code> method with the context configuration.
	 * 
	 * @param aContext the underlying procedure context
	 */
	public void visit(ProcedureContext aContext) {
		myContext = Objects.requireNonNull(aContext);
		
		accept(aContext.config());
	}
}
