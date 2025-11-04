/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm;

import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Defines functions for extending the standard merge process.
 *
 * @version 2.0.0 28.10.2025 08:49:54
 */
public interface SMClient {

	/**
	 * Maps a client to a consumer of an <code>SMGroup</code> in case of a group
	 * exit event.
	 */
	Function<SMClient, Consumer<SMGroup>> ConsumerOnExit = cl -> cl::onExit;
	/**
	 * Maps a client to a consumer of an <code>SMGroup</code> in case of a group
	 * init event.
	 */
	Function<SMClient, Consumer<SMGroup>> ConsumerOnInit = cl -> cl::onInit;
	/**
	 * Maps a client to a consumer of an <code>SMInput</code> in case of an input
	 * selected event.
	 */
	Function<SMClient, Consumer<SMInput>> ConsumerOnSelected = cl -> cl::onSelected;

	/**
	 * Performs the processing after the input data for the group has completed.
	 * 
	 * @param aGroup the group object
	 */
	void onExit(SMGroup aGroup);

	/**
	 * Performs the processing before the input data for the group is executed.
	 * 
	 * @param aGroup the group object
	 */
	void onInit(SMGroup aGroup);

	/**
	 * Performs the processing of data from the selected input channel
	 * 
	 * @param anInput the input data object
	 */
	void onSelected(SMInput anInput);

	/**
	 * Allows this client to initialize itself from the underlying procedure
	 * context.
	 */
//	void visit(SMContext aContext);
//	
//	/**
//	 * Runs a merge procedure.
//	 * 
//	 * @param aConfig the runtime options for the merge procedure
//	 */
//	default void runMerger(Config aConfig) {
//		Merger l_merger = new Merger();
//
//		l_merger.accept(aConfig);
//		l_merger.run();
//	}
//
//	/**
//	 * Runs a merge procedure.
//	 * 
//	 * @param aConfig the runtime options for the merge procedure
//	 */
//	default void runMerger(SMConfig aConfig) {
//		Config l_conf = ConfigFactory.parseMap(aConfig);
//		
//		runMerger(l_conf);
//	}
}
