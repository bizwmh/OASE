/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.sort;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import biz.oase.sm.SMConfig;
import biz.oase.sm.bundle.MSG;
import biz.oase.sm.bundle.VAL;
import biz.oase.sm.core.SM;
import biz.oase.sm.core.context.ProcedureContext;
import biz.oase.sm.merge.Merger;

/**
 * Sort worker for a file.
 *
 * @version 1.0.0 08.03.2025 15:09:39
 */
public class SWFile extends SortWorker<File, File> implements SM {

	private SWOutput csvOut;
	private String myName;

	/**
	 * Creates a default <code>SWFile</code> instance.
	 * 
	 * @param aContext the associated procedure context
	 */
	public SWFile(ProcedureContext aContext) {
		super(aContext);

		csvOut = new SWOutput(aContext);
		myName = getClass().getSimpleName() + " " + ctx.getName(); //$NON-NLS-1$
	}

	@Override
	public void dispose() {
		csvOut = null;

		super.dispose();
	}

	@Override
	public void exit() {
		super.exit();

		File l_workout = workList.get(0);
		String l_name = outputName();
		Config l_outConf = ctx.config().getConfig(l_name);
		String l_path = l_outConf.getString(PATH);
		File l_sortout = new File(l_path);
		Path l_sp = l_workout.toPath();
		Path l_tp = l_sortout.toPath();

		try {
			Files.copy(l_sp, l_tp, StandardCopyOption.REPLACE_EXISTING);
			l_workout.delete();
		}
		catch (IOException anEx) {
			throw ctx.exception(anEx);
		}
	}

	@Override
	protected File buildResult() {
		try {
			File l_ret = csvOut.generateFile();
			Map<String, Object> l_map = buildMergerMap(l_ret);
			Config l_conf = ConfigFactory.parseMap(l_map);
			Merger l_merger = new Merger();

			l_merger.accept(l_conf);
			l_merger.run();

			return l_ret;
		}
		catch (Exception anEx) {
			throw ctx.exception(MSG.SORTWORKER_ERROR, ctx.getName(), anEx.getMessage());
		}
	}

	@Override
	protected void clearWorkList() {
		workList.forEach(file -> file.delete());
		super.clearWorkList();
	}

	@Override
	protected Consumer<File> getResultConsumer() {
		return this;
	}

	private Map<String, Object> buildMergerMap(File anOutput) {
		SMConfig l_ret = new SMConfig(myName);

		l_ret.put(EXEC, MERGE);

		// group properties from context
		List<String> l_groups = ctx.groupNames();

		l_ret.group(l_groups);
		l_groups.stream()
			.filter(name -> ctx.hasPath(name))
			.forEach(name -> {
				Config l_conf = ctx.config().getConfig(name);
				l_ret.getMap(name).putAll(l_conf.root().unwrapped());
			});

		// set output
		l_ret.output(List.of(VAL.SORTOUT));
		l_ret.getMap(VAL.SORTOUT).put(PATH, anOutput.getPath());

		// set input files
		List<String> l_il = new ArrayList<String>();

		for (int i = 0; i < workList.size(); i++) {
			String l_name = ctx.getName() + "_" + i; //$NON-NLS-1$
			File l_file = workList.get(i);
			String l_path = l_file.getPath();

			l_il.add(l_name);
			l_ret.path(l_name, l_path);
			l_ret.output(l_name, VAL.SORTOUT);
			l_ret.onSelected(l_name, ON_SELECTED);
		}
		l_ret.input(l_il);

		return l_ret;
	}

	private String outputName() {
		List<String> l_list = ctx.asStringList(OUTPUT);
		
		if (l_list.size() == 0) {
			l_list.add(OUTPUT);
		}
		return l_list.get(0);
	}
}
