/* --------------------------------------------------------------------------
 * Project: CAR HTTP Client
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.http;

import biz.car.SYS;
import biz.car.XRuntimeException;

/**
 * An unchecked exception indicating an error during the execution of an HTTP
 * request.
 *
 * @version 2.0.0 04.03.2026 08:55:20
 */
public class HTTP_Exception extends XRuntimeException {

	private static final long serialVersionUID = 1L;

	/**
	 * Creates a default <code>HTTP_Exception</code> instance.
	 */
	public HTTP_Exception() {
		super();
		
		SYS.LOG.exception(this);
	}

	/**
	 * Constructs a new exception with the given detail message.
	 * 
	 * @param aMessage  the detail message of the exception
	 * @param anArgList the message variables
	 */
	public HTTP_Exception(String aMessage, Object... anArgList) {
		super(aMessage, anArgList);

		SYS.LOG.exception(aMessage, anArgList);
	}

	/**
	 * Constructs a new exception with the given cause.
	 * 
	 * @param aCause the cause of the exception
	 */
	public HTTP_Exception(Throwable aCause) {
		super(aCause);
		
		SYS.LOG.exception(aCause);
	}

	/**
	 * Constructs a new exception with the given cause and detail message.
	 * 
	 * @param aCause    the cause of the exception
	 * @param aMessage  the detail message of the exception
	 * @param anArgList the message variables
	 */
	public HTTP_Exception(Throwable aCause, String aMessage, Object... anArgList) {
		super(aCause, aMessage, anArgList);
		
		SYS.LOG.exception(aCause, aMessage, anArgList);
	}
}
