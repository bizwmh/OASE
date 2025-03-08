/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.merge.sort;

import java.util.Comparator;

import biz.car.csv.CSVRecord;
import biz.oase.sm.context.ProcedureContext;
import biz.oase.sm.core.Group;

/**
 * Compares two CSV records by comparing the associated groups.
 *
 * @version 1.0.0 08.03.2025 15:11:13
 */
public class RecordComparator implements Comparator<CSVRecord> {

	private Group g1;
	private Group g2;

	/**
	 * Creates a default <code>RecordComparator</code> instance.
	 */
	public RecordComparator(ProcedureContext aContext) {
		super();

		g1 = aContext.newGroup();
		g2 = aContext.newGroup();
	}

	@Override
	public int compare(CSVRecord aRec1, CSVRecord aRec2) {
		g1.copyOf(aRec1);
		g2.copyOf(aRec2);

		return g1.compareTo(g2);
	}

	/**
	 * Releases all allocated resources.
	 */
	public void dispose() {
		if (g1 != null) {
			g1.dispose();

			g1 = null;
		}
		if (g2 != null) {
			g2.dispose();

			g2 = null;
		}
	}
}
