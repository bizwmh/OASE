/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE System Reconciliation
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sr;

import static biz.oase.sr.bundle.VAR.ACTION;
import static biz.oase.sr.bundle.VAR.BLANK;
import static biz.oase.sr.bundle.VAR.DELETE;
import static biz.oase.sr.bundle.VAR.INSERT;
import static biz.oase.sr.bundle.VAR.NEW;
import static biz.oase.sr.bundle.VAR.OLD;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import biz.car.csv.CSVHeader;
import biz.car.csv.CSVRecord;
import biz.oase.framework.OASE;
import biz.oase.sm.SMClient;
import biz.oase.sm.SMGroup;
import biz.oase.sm.SMInput;
import biz.oase.sm.SMOutput;

/**
 * Sort/Merge client for implementing a system reconciliation process.
 *
 * @version 2.0.0 28.10.2025 08:43:49
 */
public class SRClient implements OASE, SMClient {

	private Map<String, CSVRecord> recMap;

	/**
	 * Creates a default <code>MTMain</code> instance.
	 */
	public SRClient() {
		super();
		
		recMap = new HashMap<String, CSVRecord>();
	}

	@Override
	public void onExit(SMGroup aGroup) {
		List<String> l_list = aGroup.context().inputNames();
		CSVRecord l_master = recMap.get(l_list.get(0));
		CSVRecord l_target = recMap.get(l_list.get(1));

		if (l_master == null) {
			deleteTarget(aGroup);
		} else {
			if (l_target == null) {
				insertTarget(aGroup);
			} else {
				compareRecords(aGroup);
			}
		}
	}

	@Override
	public void onInit(SMGroup aGroup) {
		recMap.clear();
	}

	@Override
	public void onSelected(SMInput anInput) {
		String l_name = anInput.getName();

		recMap.put(l_name, anInput.getCurrent());
	}

	private void compareRecords(SMGroup aGroup) {
		List<String> l_list = aGroup.context().inputNames();
		CSVRecord l_master = recMap.get(l_list.get(0));
		CSVRecord l_target = recMap.get(l_list.get(1));
		CSVHeader l_hdr = l_target.hdr;
		Map<String, String> l_map = l_hdr.columns().stream()
			.filter(field -> l_master.hasField(field))
			.filter(field -> {
				String l_mfield = l_master.getValue(field);
				String l_tfield = l_target.getValue(field);

				return !l_mfield.equalsIgnoreCase(l_tfield);
			})
			.collect(Collectors.toMap(
				field -> field,
				field -> l_master.getValue(field)));

		if (l_map.size() > 0) {
			SMInput l_in = aGroup.context().getInput(l_list.get(1));
			String l_outName = l_in.getString(OUTPUT);
			SMOutput l_out = aGroup.context().getOutput(l_outName);
			CSVRecord l_rec = l_out.getCurrent();
			l_hdr = l_rec.hdr;

			l_rec.put(l_target);
			l_rec.setValue(ACTION, OLD);
			l_out.write();
			l_map.put(ACTION, NEW);
			aGroup.context().groupNames().forEach(group -> {
				l_map.put(group, l_master.getValue(group));
			});
			l_map.entrySet().forEach(entry -> {
				if (entry.getValue().trim().length() == 0) {
					l_map.put(entry.getKey(), BLANK);
				}
			});
			l_out.write(l_hdr.Record(l_map));
		}
	}

	private void deleteTarget(SMGroup aGroup) {
		List<String> l_list = aGroup.context().inputNames();
		String l_target = l_list.get(1);
		SMInput l_in = aGroup.context().getInput(l_target);
		String l_outName = l_in.getString(OUTPUT);
		SMOutput l_out = aGroup.context().getOutput(l_outName);
		CSVRecord l_rec = l_out.getCurrent();

		l_rec.put(recMap.get(l_target));
		l_rec.setValue(ACTION, DELETE);
		l_out.write();
	}

	private void insertTarget(SMGroup aGroup) {
		List<String> l_list = aGroup.context().inputNames();
		String l_master = l_list.get(0);
		SMInput l_in = aGroup.context().getInput(l_master);
		String l_outName = l_in.getString(OUTPUT);
		SMOutput l_out = aGroup.context().getOutput(l_outName);
		CSVRecord l_rec = l_out.getCurrent();

		l_rec.put(recMap.get(l_master));
		l_rec.setValue(ACTION, INSERT);
		l_out.write();
	}
}
