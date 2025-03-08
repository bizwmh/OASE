/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 *          SORT/MERGE Service
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.sm.core;

import java.io.IOException;

import biz.car.XRuntimeException;
import biz.car.csv.CSVRecord;
import biz.oase.sm.bundle.MSG;

/**
 * An <code>Channel</code> is the vehicle for a SORT/MERGE process to receive or
 * send application data objects.<br>
 * As such this class defines the basic operations for both input and output
 * channels.
 * <p>
 * IO channel operations are based on CSV records.
 *
 * @version 1.0.0 04.11.2024 14:14:23
 */
public abstract class Channel extends XObject {

	/**
	 * Creates a <code>Channel</code> instance with the given name.
	 * 
	 * @param aName the name for this channel
	 */
	public Channel(String aName) {
		super(aName);
	}

	/**
	 * Closing a channel means that allocated system resources are freed. If the
	 * channel is not open this method does nothing. A closed channel is no longer
	 * able to perform IO operations.
	 *
	 * @throws XRuntimeException if close fails
	 */
	public void close() {
		try {
			doClose();
			closeMessage();
		} catch (XRuntimeException anEx) {
			throw anEx;
		} catch (Throwable anEx) {
			throw exception(MSG.ClOSE_ERROR, getLabel(), getName(), getRecordCount());
		}
	}

	/**
	 * @return the current CSV record.<br>
	 *         For input channels this is the last read record.<br>
	 *         For output channels this is the next container to be written.
	 */
	public abstract CSVRecord getCurrent();

	/**
	 * @return the number of records already processed by this channel
	 */
	public abstract int getRecordCount();

	/**
	 * Opens this channel.<br>
	 * After open the channel is ready to perform IO operations.
	 *
	 * @throws SPException if open fails
	 */
	public void open() {
		try {
			doOpen();
			openMessage();
		} catch (XRuntimeException anEx) {
			throw anEx;
		} catch (Throwable anEx) {
			throw exception(MSG.OPEN_ERROR, getLabel(), getName());
		}
	}

	/**
	 * Sends a message to the log about the opended channel.
	 */
	protected void closeMessage() {
		info(MSG.CHANNEL_ClOSED, getLabel(), getName(), getRecordCount());
	}

	/**
	 * Performs the close operation
	 * 
	 * @throws IOException
	 */
	protected abstract void doClose() throws IOException;

	/**
	 * Performs the open operation.
	 * 
	 * @throws IOException
	 */
	protected abstract void doOpen() throws IOException;

	/**
	 * Sends a message to the log about the opended channel.
	 */
	protected void openMessage() {
		info(MSG.CHANNEL_OPENED, getLabel(), getName(), getString(PATH));
	}
}
