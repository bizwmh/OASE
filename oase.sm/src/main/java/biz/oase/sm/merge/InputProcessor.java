/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.merge;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.IntPredicate;

import biz.car.XRuntimeException;
import biz.car.csv.CSVRecord;
import biz.oase.sm.SMClient;
import biz.oase.sm.SMInput;
import biz.oase.sm.bundle.MSG;
import biz.oase.sm.core.Group;
import biz.oase.sm.core.Input;
import biz.oase.sm.core.SM;
import biz.oase.sm.core.WriteToOut;
import biz.oase.sm.core.context.ProcedureContext;

/**
 * Manages the processing of data from an input channel of the merge procedure.
 *
 * @version 2.0.0 21.10.2025 09:16:02
 */
public class InputProcessor implements SM, Runnable {

	
	private static Consumer<SMInput> nop = in -> WriteToOut.accept(in);
	private static Map<Boolean, IntPredicate> pMap;
	private static IntPredicate RegularSortOrder = n -> !(n < 0);
	private static IntPredicate StrictSortOrder = n -> n > 0;
	static {
		pMap = new HashMap<Boolean, IntPredicate>();

		pMap.put(true, StrictSortOrder);
		pMap.put(false, RegularSortOrder);
	}

	private Consumer<SMInput> inputConsumer;
	private Group myGroup;
	private Input myInput;
	private String name;
	private Group oldGroup;

	/**
	 * Creates an <code>InputProcessor</code> instance.
	 */
	public InputProcessor(String aName) {
		super();

		name = aName;
		inputConsumer = nop;
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
		processInput();
		readNextRecord();
		checkSortOrder();
	}

	public void visit(ProcedureContext aContext, ClientManager aManager) {
		myInput = aContext.getInput(name);
		myGroup = aContext.newGroup();
		oldGroup = aContext.newGroup();
		String l_selected = myInput.getString(ON_SELECTED, null);
		
		if (l_selected != null) {
			SMClient l_client = aManager.getClient(l_selected);
			inputConsumer = SMClient.ConsumerOnSelected.apply(l_client);
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
		inputConsumer.accept(myInput);
	}
}
