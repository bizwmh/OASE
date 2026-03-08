/* --------------------------------------------------------------------------
 * Project: CAR HTTP Client
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.http;

/**
 * Interface to the response object of an HTTP request.
 *
 * @version 2.0.0 04.03.2026 08:54:01
 */
public interface HTTP_Response {

	/**
	 * @return the body of the response as a plain text.
	 */
	String body();

	/**
	 * Looks up a header value
	 * 
	 * @param aName the name of the header
	 * @return the header value found or <code>null</code>
	 */
	String header(String aName);

	/**
	 * @return the HTTP response code
	 */
	int httpCode();

	/**
	 * @return true<code>true</code> if the HTTP request completed successfully
	 * */ 
	default boolean isOK() {
      return httpCode() >= 200 && httpCode() < 300;
  }
}
