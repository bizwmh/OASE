/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm;

/**
 * Defines functions for extending the standard merge process.
 *
 * @version 1.0.0 08.03.2025 14:27:07
 */
public interface SMClient {

	/**
	 * Performs the processing after the input data for the group has completed.
	 * 
	 * @param anObject the group object
	 */
	void onExitGroup(SMObject anObject);

	/**
	 * Performs the processing before the input data for the group is executed.
	 * 
	 * @param anObject the group object
	 */
	void onInitGroup(SMObject anObject);

	/**
	 * Performs the processing of data from the selected input channel
	 * 
	 * @param anObject the input data object
	 */
	void onSelectedInput(SMObject anObject);
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
