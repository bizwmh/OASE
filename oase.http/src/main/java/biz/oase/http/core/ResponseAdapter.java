/* --------------------------------------------------------------------------
 * Project: CAR HTTP Client
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.http.core;

import java.io.IOException;

import okhttp3.Response;

import biz.oase.http.HTTP_Exception;
import biz.oase.http.HTTP_Response;

/**
 * Wrapper for the original HTTP response.
 *
 * @version 2.0.0 04.03.2026 11:55:05
 */
public class ResponseAdapter implements HTTP_Response {

	private Response theResponse;

	/**
	 * Creates a default <code>ResponseAdapter</code> instance.
	 * 
	 * @param aResponse the original HTTP response
	 */
	public ResponseAdapter(Response aResponse) {
		super();

		theResponse = aResponse;
	}

	@Override
	public String body() {
		try {
			return theResponse.body().string();
		} catch (IOException anEx) {
			throw new HTTP_Exception(anEx);
		}
	}

	/**
	 * Looks up a header value
	 * 
	 * @param aName the name of the header
	 * @return the header value found or <code>null</code>
	 */
	@Override
	public String header(String aName) {
		return theResponse.header(aName);
	}
	
	/**
	 * @return the HTTP response code
	 */
	@Override
	public int httpCode() {
		return theResponse.code();
	}
}
