/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.bundle;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import biz.car.CAR;
import biz.oase.sm.SM;
import biz.oase.sm.core.Group;

/**
 * Functions to convert a string value to a defined data type.
 *
 * @version 1.0.0 08.03.2025 14:28:38
 */
public class ValueType {

	private static Map<String, DateFormat> dfMap;
	private static Function<Group, Comparable<?>> ToDate = g -> {
		try {
			String l_format = g.getString(SM.FORMAT, CAR.DF_DATE);
			DateFormat l_df = dfMap.get(l_format);

			if (l_df == null) {
				l_df = new SimpleDateFormat(l_format);

				dfMap.put(l_format, l_df);
			}
			return l_df.parse(g.getValue());
		} catch (ParseException anEx) {
			throw g.exception(anEx);
		}
	};

	private static Function<Group, Comparable<?>> ToInteger = g -> {
		try {
			return Integer.parseInt(g.getValue());
		} catch (Exception anEx) {
			throw g.exception(anEx);
		}
	};

	private static Map<String, Function<Group, Comparable<?>>> typeMap;

	static {
		typeMap = new HashMap<>();
		dfMap = new HashMap<>();

		typeMap.put(VAL.integer, ToInteger);
		typeMap.put(VAL.date, ToDate);
	}

	/**
	 * Converts the given group to a <code>Comparable</code> based on the data type
	 * of the group value.
	 * 
	 * @param aGroup the group to convert
	 * @return the {@link Comparable}
	 */
	public static Comparable<?> toComparable(Group aGroup) {
		Comparable<?> l_ret = aGroup.getValue();

		if (aGroup.hasPath(SM.TYPE)) {
			String l_type = aGroup.getString(SM.TYPE);
			Function<Group, Comparable<?>> l_func = typeMap.get(l_type);

			if (l_func == null) {
				throw aGroup.exception(MSG.INVALID_DATATYPE, l_type, aGroup.getName());
			} else {
				l_ret = l_func.apply(aGroup);
			}
		}
		return l_ret;
	}
}
