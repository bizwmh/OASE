/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.typesafe.config.ConfigException;

import biz.car.io.FieldSource;
import biz.oase.sm.SMGroup;
import biz.oase.sm.bundle.MSG;
import biz.oase.sm.bundle.VAL;
import biz.oase.sm.bundle.ValueType;
import biz.oase.sm.core.context.ProcedureContext;

/**
 * A Sort/Merge data group.<br>
 * A group is defined by one or multiple ordered fields of an input record.<br>
 * As such a group defines a hierarchy of fields.
 * <p>
 * A <code>Group</code> instance exists for each field in the group.<br>
 * A record belongs to the group if the field values of the record match the
 * values of this group and all its parent groups.
 *
 * @version 1.0.0 08.03.2025 14:56:35
 */
public class Group extends SMObject implements
		SMGroup,
		Comparable<Group>,
		FieldSource {

	/**
	 * The name of the dummy group.
	 */
	public static final String DUMMY = VAL.DUMMY;

	private Map<String, Group> groupMap;
	private List<String> names;
	private Group parent;
	private String value;

	/**
	 * Creates a default <code>Group</code> instance.
	 * 
	 * @param aName the name of this group
	 */
	public Group(String aName) {
		super(aName);

		names = new ArrayList<String>();
		groupMap = new HashMap<String, Group>();

		names.add(aName);
		groupMap.put(aName, this);
	}

	/**
	 * Creates a <code>Group</code> instance.
	 * 
	 * @param aName   the name of this group
	 * @param aParent the parent group of this group
	 */
	public Group(String aName, Group aParent) {
		super(aName);

		parent = aParent;
		names = parent.names;
		groupMap = parent.groupMap;

		names.add(aName);
		groupMap.put(aName, this);
	}

	@Override
	public int compareTo(Group aGroup) {
		if (!getName().equals(aGroup.getName())) {
			throw exception(MSG.GROUP_LEVEL_MISMATCH, getName(), aGroup.getName());
		}
		int l_ret = 0;

		if (parent != null) {
			Group l_parentTo = aGroup.parent;
			l_ret = parent.compareTo(l_parentTo);
		}
		if (l_ret == 0) {
			Comparable<Comparable<?>> l_value = toComparable();
			Comparable<Comparable<?>> l_valueTo = aGroup.toComparable();
			l_ret = l_value.compareTo(l_valueTo);
			boolean l_desc = getBool(DESCENDING, false);

			if (l_desc) {
				l_ret = -l_ret;
			}
		}
		return l_ret;
	}

	/**
	 * Copies all group values from the source.
	 * 
	 * @param aSource the data source for the group
	 */
	public void copyOf(FieldSource aSource) {
		names.forEach(name -> groupMap.get(name).valueOf(aSource));
	}

	@Override
	public void dispose() {
		if (parent != null) {
			parent.dispose();

			parent = null;
			names = null;
			groupMap = null;
		} else {
			if (names != null) {
				names.clear();

				names = null;
			}
			if (groupMap != null) {
				groupMap.clear();

				groupMap = null;
			}
		}
		super.dispose();
	}

	/**
	 * Provide a value which will indicate that this group will no longer receive
	 * data values from an input.
	 * 
	 * @return the value indicating the END-OF-INPUT for the associated data group.
	 *         This value is depending on the sort order.
	 */
	public String endOfInput() {
		String l_ret = HIGH_VALUE;
		boolean l_desc = getBool(DESCENDING, false);

		if (l_desc) {
			l_ret = LOW_VALUE;
		}
		return l_ret;
	}

	@Override
	public boolean equals(Object anObject) {
		boolean l_ret = false;

		if (anObject instanceof Group) {
			Group l_group = (Group) anObject;
			int l_comp = compareTo(l_group);
			l_ret = l_comp == 0;
		}
		return l_ret;
	}

	@Override
	public List<String> fieldNames() {
		return List.copyOf(names);
	}

	/**
	 * Provides access to a group in this group hierarchy by name.
	 * 
	 * @param aName the name of the group
	 * @return the group with the given name
	 * @throws SPException if a group with the given name does not exist
	 */
	public Group getGroup(String aName) {
		if (groupMap.containsKey(aName)) {
			return groupMap.get(aName);
		}
		throw exception(MSG.GROUP_NOT_FOUND, aName);
	}

	/**
	 * Gets the current value of this group.
	 * 
	 * @return the value stored in this group. May be <code>null</code>.
	 */
	public String getValue() {
		return value;
	}

	@Override
	public String getValue(String aName) {
		String l_ret = null;
		Group l_group = groupMap.get(aName);

		if (l_group != null) {
			l_ret = l_group.getValue();
		}
		return l_ret;
	}

	@Override
	public boolean hasField(String aName) {
		return groupMap.containsKey(aName);
	}

	@Override
	public int hashCode() {
		return toString().hashCode();
	}

	@Override
	public Optional<String> optionalValue(String aName) {
		String l_ret = getValue(aName);

		return Optional.ofNullable(l_ret);
	}

	@Override
	public String toString() {
		String l_ret = getName() + "=" + getValues().toString(); //$NON-NLS-1$

		return l_ret;
	}

	/**
	 * Copies the value with this group name from the source.<br>
	 * If the source value is <code>null</code> then the end-of-input is stored as
	 * this group value.
	 * 
	 * @param aSource the data source for the group
	 */
	public void valueOf(FieldSource aSource) {
		if (aSource == null) {
			value = endOfInput();
		} else {
			if (aSource.hasField(getName())) {
				value = aSource.getValue(getName());
			} else {
				if (getName() == DUMMY) {
					value = ""; //$NON-NLS-1$
				} else {
					throw new ConfigException.Missing(getName());
				}
			}
		}
	}

	@Override
	public void visit(ProcedureContext aContext) {
		if (parent != null) {
			parent.visit(aContext);
		}
		super.visit(aContext);
	}

	/**
	 * Gets a list of group values.<br>
	 * The list starts with the value from the top level group up to this group.
	 * 
	 * @return the list of group values. The level of the group is also the index
	 *         for the list elements.
	 */
	private List<String> getValues() {
		int l_level = names.indexOf(getName());

		return names.stream()
				.limit(l_level + 1)
				.map(name -> groupMap.get(name).getValue())
				.collect(Collectors.toList());
	}

	@SuppressWarnings({
			"rawtypes", "unchecked"
	})
	private Comparable<Comparable<?>> toComparable() {
		Comparable l_ret = ValueType.toComparable(this);

		return l_ret;
	}
}
