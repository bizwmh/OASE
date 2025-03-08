/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.merge;

import java.util.HashMap;
import java.util.Map;
import java.util.function.IntPredicate;

import biz.car.XRuntimeException;
import biz.car.csv.CSVRecord;
import biz.oase.sm.SM;
import biz.oase.sm.SMClient;
import biz.oase.sm.bundle.MSG;
import biz.oase.sm.context.ProcedureContext;
import biz.oase.sm.core.Group;
import biz.oase.sm.core.Input;
import biz.oase.sm.core.WriteToOut;

/**
 * Manages the processing of data from an input channel of the merge procedure.
 *
 * @version 1.0.0 08.03.2025 15:05:21
 */
public class InputProcessor implements SM, Runnable {

	private static Map<Boolean, IntPredicate> pMap;
	private static IntPredicate RegularSortOrder = n -> !(n < 0);
	private static IntPredicate StrictSortOrder = n -> n > 0;

	static {
		pMap = new HashMap<Boolean, IntPredicate>();

		pMap.put(true, StrictSortOrder);
		pMap.put(false, RegularSortOrder);
	}

	private SMClient myClient;
	private Group myGroup;
	private Input myInput;
	private String name;
	private Group oldGroup;
	private Group procedureGroup;

	/**
	 * Creates an <code>InputProcessor</code> instance.
	 */
	public InputProcessor(String aName) {
		super();

		name = aName;
	}

	/**
	 * @return the group of the associated input channel
	 */
	public Group group() {
		return myGroup;
	}

	/**
	 * Reads the next record from the input channel. The input group is set with the
	 * values from the input record.
	 */
	public void readNextRecord() {
		CSVRecord l_rec = myInput.readNext();

		oldGroup.copyOf(myGroup);
		myGroup.copyOf(l_rec);
	}

	@Override
	public void run() {
		do {
			processInput();
			readNextRecord();
			checkSortOrder();
		} while (procedureGroup.compareTo(myGroup) == 0);
	}

	public void visit(ProcedureContext aContext) {
		myGroup = aContext.inputGroup(name);
		myInput = aContext.getInput(name);
		procedureGroup = aContext.procedureGroup();
		oldGroup = aContext.newGroup();

		if (myInput.hasPath(ON_SELECTED)) {
			myClient = aContext.client(myInput.getString(ON_SELECTED));
		}
	}

	/**
	 * Verifies the sort order of the groups.
	 * 
	 * @throws XRuntimeException if the sort order is not correct
	 */
	private void checkSortOrder() {
		boolean l_unique = myInput.getBool(UNIQUE_KEY, false);
		int l_comp = myGroup.compareTo(oldGroup);
		IntPredicate l_ip = pMap.get(l_unique);

		if (l_ip.test(l_comp) == false) {
			String l_input = myInput.getName();

			throw myInput.exception(MSG.SORT_ERROR, l_input, oldGroup.toString(),
					myGroup.toString());
		}
	}

	/**
	 * Processes the record from the currently selected input channel.<br>
	 * This implementation invokes the input consumer found for the
	 * <code>SM.ON_SELECTED</code> key.
	 */
	private void processInput() {
		if (myClient == null) {
			WriteToOut.accept(myInput);
		} else {
			myClient.onSelectedInput(myInput);
		}
	}
}
