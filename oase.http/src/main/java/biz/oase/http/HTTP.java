/* --------------------------------------------------------------------------
 * Project: CAR HTTP Client
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.http;

import biz.car.CAR;
import biz.oase.http.core.RequestAdapter;
import biz.oase.http.core.RequestDTO;

/**
 * HTTP Bundle Constants
 *
 * @version 2.0.0 12.02.2026 14:14:57
 */
public interface HTTP {
	/**
	 * Indicates a failed execution of a HTTP request.
	 */
	String KO = CAR.KO;

	/**
	 * Indicates a successful execution of a HTTP request.
	 */
	String OK = CAR.OK;

	/**
	 * Request using the HTTP DELETE method.
	 */
	static HTTP_Request DELETE() {
		return new RequestAdapter().delete();
	}

	/**
	 * Request using the HTTP GET method.
	 */
	static HTTP_Request GET() {
		return new RequestAdapter().get();
	}

	/**
	 * Request using the HTTP PATCH method.
	 */
	static HTTP_Request PATCH() {
		return new RequestAdapter().patch();
	}

	/**
	 * Request using the HTTP POST method.
	 */
	static HTTP_Request POST() {
		return new RequestAdapter().post();
	}

	/**
	 * Initial and empty HTTP request.
	 */
	static HTTP_Request REQUEST() {
		return new RequestAdapter();
	}

	/**
	 * Request built based on the request DTO.
	 */
	static HTTP_Request REQUEST(HTTP_RequestDTO aDTO) {
		HTTP_Request l_ret = RequestDTO.newRequest(aDTO);

		return l_ret;
	}
}
