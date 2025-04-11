/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          OASE Dataspace Gateway
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.ds;

import java.util.function.Consumer;

import biz.car.io.DataRecord;
import biz.oase.ds.bundle.MSG;

/**
 * Represents the outcome of a data space operation.
 *
 * @version 1.0.0 20.03.2025 10:26:54
 */
public class DSResult implements DS, Consumer<DataRecord>  {

	private int errCode;
	private String errMsg;
	private DataRecord rec;
	private Object ro;
	private String status;

	/**
	 * Create a new <code>TSResult</code> instance with status OK.
	 */
	public DSResult() {
		super();

		status = OK;
		errMsg = ""; //$NON-NLS-1$
		errCode = 0;
	}

	/**
	 * Add a copy of the given record to this result.
	 * 
	 * @param aRecord the target system record to be added to this result.
	 */
	@Override
	public void accept(DataRecord aRecord) {
		rec = aRecord;
	}

	/**
	 * @return the error message
	 */
	public String errorMessage() {		
		if (errMsg.length() == 0) {
			switch (errCode) {
			case NOT_FOUND:
				errMsg = MSG.NOT_FOUND;

				break;

			case DUPLICATE_KEY:
				errMsg = MSG.DUPLICATE_KEY;
				
				break;

			default:
				break;
			}
		}
		return errMsg;
	}

	/**
	 * Provides the single record created by the underlying target system operation.
	 * 
	 * @return the target system record or <code>null</code> if the operation did
	 *         not create a record.
	 */
	public DataRecord record() {
		return rec;
	}

	/**
	 * @return the response
	 */
	@SuppressWarnings("unchecked")
	public <T> T resultObject() {
		return (T) ro;
	}

	/**
	 * Sets the error message. Status is set to <code>ERROR</code>.
	 * 
	 * @param aMessage the messgae text
	 */
	public void setError(String aMessage) {
		errMsg = aMessage;
		status = KO;
	}

	/**
	 * Mark the result as failed.
	 * 
	 * @param aReason the reason for the failure
	 */
	public void setKO(int aReason) {
		status = KO;
		errCode = aReason;
	}

	/**
	 * @param the result object to set
	 */
	public void setResultObject(Object anObject) {
		ro = anObject;
	}

	/**
	 * @return the status of the underlying target system operation.
	 */
	public String status() {
		return status;
	}
}
