/* --------------------------------------------------------------------------
 * Project: OASE JDBC
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.jdbc.core;

import java.util.Optional;

import biz.oase.jdbc.SQLResult;

/**
 * Implements {@link SQLResult}.
 * 
 * @version 2.0.0 02.03.2026 11:56:50
 */
public class Result implements SQLResult {

	static Result failure(long aTimeMs, Exception anEx) {
		Result l_ret = new Result();
		l_ret.executionTimeMs = aTimeMs;
		l_ret.failure = Optional.ofNullable(anEx);
		l_ret.updateCount = -1;

		return l_ret;
	}

	static Result success(long aTimeMs, int aUpdateCount) {
		Result l_ret = new Result();
		l_ret.executionTimeMs = aTimeMs;
		l_ret.failure = Optional.ofNullable(null);
		l_ret.updateCount = aUpdateCount;

		return l_ret;
	}

	private long executionTimeMs;
	private Optional<Exception> failure;
	private int updateCount;

	/**
	 * Creates a default <code>Result</code> instance.
	 */
	public Result() {
		super();
	}

	@Override
	public long executionTimeMs() {
		return executionTimeMs;
	}

	@Override
	public Optional<Exception> failure() {
		return failure;
	}

	@Override
	public boolean isOK() {
		return failure.isEmpty();
	}

	@Override
	public int updateCount() {
		return updateCount;
	}
}
