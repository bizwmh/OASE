/* --------------------------------------------------------------------------
 * Viessmann  
 * -------------------------------------------------------------------------- */

package com.viessmann.srrs.function;

import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import com.viessmann.srrs.RS;
import com.viessmann.srrs.bundle.BND;
import com.viessmann.srsm.SM;
import com.viessmann.srsm.core.ChannelContext;
import com.viessmann.srsm.core.SMGroup;
import com.viessmann.srsm.core.SMInput;
import com.viessmann.srsm.core.SMOutput;
import com.viessmann.srsp.io.csv.CSVHeader;
import com.viessmann.srsp.io.csv.CSVRecord;

/**
 * TODO comment
 *
 * @version 1.0.0 30.06.2022 10:18:42
 */
public class MTonExit implements Consumer<SMGroup> {

	private SMOutput out;

	/**
	 * Creates a default <code>MTonExit</code> instance.
	 */
	public MTonExit() {
		super();
	}

	@Override
	public void accept(SMGroup aGroup) {
		Object l_master = aGroup.get(BND.MASTER);
		Object l_target = aGroup.get(BND.TARGET);

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

	private void compareRecords(SMGroup aGroup) {
		CSVRecord l_master = (CSVRecord) aGroup.get(BND.MASTER);
		CSVRecord l_target = (CSVRecord) aGroup.get(BND.TARGET);
		CSVHeader l_hdr = l_target.header;
		Map<String, String> l_map = l_hdr.asList().stream()
			.filter(field -> l_master.fieldIndex(field) != -1)
			.filter(field -> {
				String l_mfield = l_master.getFieldValue(field).get();
				String l_tfield = l_target.getFieldValue(field).get();

				return !l_mfield.equalsIgnoreCase(l_tfield);
			})
			.collect(Collectors.toMap(
				field -> field,
				field -> l_master.getFieldValue(field).get()));

		if (l_map.size() > 0) {
			SMOutput l_out = getOutput(aGroup);
			CSVRecord l_rec = l_out.getCurrent();
			l_hdr = l_rec.header;

			l_out.put(l_target);
			l_rec.set(RS.ACTION, RS.OLD);
			l_out.write();
			l_map.put(RS.ACTION, RS.NEW);
			aGroup.groupNames().forEach(group -> {
				l_map.put(group, l_master.getFieldValue(group).get());
			});
			l_map.entrySet().forEach(entry -> {
				if (entry.getValue().trim().length() == 0) {
					l_map.put(entry.getKey(), RS.BLANK);
				}
			});
			l_out.write(l_hdr.newRecord(l_map));
		}
	}

	private void deleteTarget(SMGroup aGroup) {
		CSVRecord l_target = (CSVRecord) aGroup.get(BND.TARGET);
		SMOutput l_out = getOutput(aGroup);
		CSVRecord l_rec = l_out.getCurrent();

		l_out.put(l_target);
		l_rec.set(RS.ACTION, RS.DELETE);
		l_out.write();
	}

	private SMOutput getOutput(SMGroup aGroup) {
		if (out == null) {
			ChannelContext l_ctx = aGroup.context().getChannelContext();
			String l_target = l_ctx.inputNames().get(1);
			SMInput l_input = l_ctx.getInput(l_target);
			String l_out = l_input.getString(SM.OUTPUT);
			out = l_ctx.getOutput(l_out);
		}
		return out;
	}

	private void insertTarget(SMGroup aGroup) {
		CSVRecord l_master = (CSVRecord) aGroup.get(BND.MASTER);
		SMOutput l_out = getOutput(aGroup);
		CSVRecord l_rec = l_out.getCurrent();

		l_out.put(l_master);
		l_rec.set(RS.ACTION, RS.INSERT);
		l_out.write();
	}
}
