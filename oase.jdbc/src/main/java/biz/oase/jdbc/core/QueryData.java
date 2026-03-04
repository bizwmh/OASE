/* --------------------------------------------------------------------------
 * Project: OASE JDBC
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.jdbc.core;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import biz.oase.jdbc.SQLQueryData;

/**
 * TODO QueryData comment
 *
 * @version 2.0.0 03.03.2026 19:21:20
 */
public class QueryData implements SQLQueryData {

	private final List<String> labels = new ArrayList<>();
	private final Map<String, Object> values = new LinkedHashMap<>();

	/**
	 * Creates a default <code>QueryData</code> instance.
	 */
	public QueryData() {
		super();
	}

	/**
	 * TODO add
	 * 
	 * @param aName
	 * @param aValue
	 */
	public void add(String aName, Object aValue) {
		labels.add(aName);
		values.put(aName, aValue);
	}

	@Override
	public BigDecimal getBigDecimal(String aLabel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getBoolean(String aLabel) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public LocalDate getDate(String aLabel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getDouble(String aLabel) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getInt(String aLabel) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long getLong(String aLabel) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getString(int aIndex) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getString(String aLabel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalDateTime getTimestamp(String aLabel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getValue(String aLabel) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> labels() {
		// TODO Auto-generated method stub
		return null;
	}

}
