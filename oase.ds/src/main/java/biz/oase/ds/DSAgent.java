/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Dataspace Interface
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds;

import java.util.Iterator;

import com.typesafe.config.Config;

import biz.car.config.ConfigAdapter;
import biz.car.csv.CSVRecord;

/**
 * Performs operations on a subset of the application data objects.
 *
 * @version 2.0.0 23.11.2025 16:11:35
 */
public abstract class DSAgent extends ConfigAdapter {

	/**
	 * Creates a default <code>DSAgent</code> instance.
	 * 
	 * @param aName the name of the agent
	 */
	public DSAgent(String aName) {
		super(aName);
	}

	@Override
	public void accept(Config aConfig) {
		Config l_conf = aConfig;
		String l_name = getName();

		if (aConfig.hasPath(l_name)) {
			l_conf = aConfig.getConfig(l_name);
		}
		super.accept(l_conf);
	}

	/**
	 * Removes an entry from the dataspace.
	 * 
	 * @param aRecord the record representing the data entry.
	 * @return a <code>DSResult</code> object.
	 */
	public abstract DSResult delete(CSVRecord aRecord);

	/**
	 * Releases all allocated resources.
	 */
	public abstract void dispose();

	/**
	 * Inserts an new entry into the dataspace.
	 * 
	 * @param aRecord the record representing the data entry.
	 * @return the result containing the table entry related record.
	 */
	public abstract DSResult insert(CSVRecord aRecord);

	/**
	 * Establishes a communication channel to the dataspace.
	 */
	public abstract void openConnection();

	/**
	 * Runs a query on the application data space.
	 * 
	 * @return An iterator over the result set of the query
	 */
	public abstract Iterator<CSVRecord> query();

	/**
	 * Looks up a data entry.<br>
	 * The key values are taken from the fields in the given record.
	 * 
	 * @param aRecord the record holding the fields to use for the read operation.
	 * @return the result containing the user record.<br>
	 *         The read operation must return exactly one record.
	 */
	public abstract DSResult read(CSVRecord aRecord);

	/**
	 * Updates an entry in the dataspace.
	 * 
	 * @param aRecord the record representing the data entry.
	 * @return the result containing the user related record.
	 */
	public abstract DSResult update(CSVRecord aRecord);
}
