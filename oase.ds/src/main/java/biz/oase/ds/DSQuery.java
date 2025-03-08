/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Dataspace Gateway
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds;

import java.util.Iterator;

import biz.car.csv.CSVRecord;

/**
 * An iterator over the result set of a data space query.
 *
 * @version 1.0.0 02.03.2025 17:09:35
 */
public interface DSQuery extends Iterator<CSVRecord> {

}