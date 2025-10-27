/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.sort;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import biz.oase.sm.bundle.BND;
import biz.oase.sm.core.context.ProcedureContext;

/**
 * Base class for the 3 types of sort workers.<br>
 *
 * @version 1.0.0 08.03.2025 15:10:24
 */
public abstract class SortWorker<T, R> implements Consumer<T> {

	protected ProcedureContext ctx;
	protected List<T> workList;

	/**
	 * Creates a default <code>SortWorker</code> instance.
	 * 
	 * @param aContext the associated procedure context
	 */
	public SortWorker(ProcedureContext aContext) {
		super();

		ctx = aContext;
		workList = new ArrayList<T>();
	}

	@Override
	public void accept(T anItem) {
		if (workList.size() < BND.SORT_WORK_LIMIT) {
			workList.add(anItem);
		} else {
			R l_result = buildResult();

			if (l_result != null) {
				Consumer<R> l_consumer = getResultConsumer();

				clearWorkList();
				l_consumer.accept(l_result);
				workList.add(anItem);
			}
		}
	}

	/**
	 * Releases all allocated resources.
	 */
	public void dispose() {
		if (workList != null) {
			workList.clear();

			workList = null;
		}
	}

	/**
	 * Performs the finalization of this worker.<br>
	 * If the internal list of sortable items is not empty this list is processed by
	 * the associated result consumer.
	 */
	public void exit() {
		if (workList.size() > 0) {
			R l_result = buildResult();

			if (l_result != null) {
				Consumer<R> l_consumer = getResultConsumer();

				clearWorkList();
				l_consumer.accept(l_result);
			}
		}
	}

	/**
	 * Processes the internal list of items to be sorted.
	 * 
	 * @return the result built from the internal list of items to be sorted.
	 */
	protected abstract R buildResult();

	/**
	 * Clears the internal cache of sort work entries.
	 */
	protected void clearWorkList() {
		workList.clear();
	}

	/**
	 * @return a reference to the processor of the result produced by this worker.
	 */
	protected abstract Consumer<R> getResultConsumer();
}
