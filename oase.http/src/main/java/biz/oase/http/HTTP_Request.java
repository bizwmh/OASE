/* --------------------------------------------------------------------------
 * Project: CAR HTTP Client
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.http;

import java.io.File;

/**
 * An HTTP request.
 * <p>
 * Instances of this interface are immutable.
 *
 * @version 2.0.0 04.03.2026 08:54:38
 */
public interface HTTP_Request {

	/**
	 * Creates a header for basic authentication.
	 * 
	 * @param aUser the user name
	 * @param aPW   the user password
	 * @return a new <code>HTTP_Request</code> instance.
	 */
	HTTP_Request basicAuth(String aUser, String aPW);

	/**
	 * Sets the request body.
	 * 
	 * @param aBody the body as a string
	 * @return a new <code>HTTP_Request</code> instance.
	 */
	HTTP_Request body(String aBody);

	/**
	 * Sets the request body.
	 * 
	 * @param aFile the file holding the request body.
	 * @return a new <code>HTTP_Request</code> instance.
	 */
	HTTP_Request bodyFile(File aFile);

	/**
	 * Adds the content type header.
	 * 
	 * @param aType the string for the content type
	 * @return a new <code>HTTP_Request</code> instance.
	 * @throws HTTP_Exception if <code>aType</code> is not a well-formed media type
	 */
	HTTP_Request contentType(String aType);

	/**
	 * Sends the HTTP request to the target server and reads the response.<br>
	 * The underlying HTTP client blocks until the response is received.
	 * 
	 * @return the response from the target server
	 */
	HTTP_Response execute();

	/**
	 * Adds an entry to a url-encoded form body.
	 * 
	 * @param aKey   the key of the entry
	 * @param aValue the value of the entry
	 * @return the new <code>HTTP_Request</code> instance
	 */
	HTTP_Request formParameter(String aKey, String aValue);

	/**
	 * Adds a header with name and value.
	 * 
	 * @param aName  the header name
	 * @param aValue the header value
	 * @return a new <code>HTTP_Request</code> instance.
	 */
	HTTP_Request header(String aName, String aValue);

	/**
	 * Sets the request method.
	 * 
	 * @param aMethod the name of the mehtod
	 * @return a new <code>HTTP_Request</code> instance.
	 */
	HTTP_Request method(String aMethod);

	/**
	 * Encodes the query parameter using UTF-8.<br>
	 * The resulting string is added to the query parameter part of the URL.
	 * 
	 * @param aKey   the parameter key
	 * @param aValue the parameter value
	 * @return a new <code>HTTP_Request</code> instance.
	 */
	HTTP_Request queryParameter(String aKey, String aValue);

	/**
	 * Creates a header for authentication via access token.
	 * 
	 * @param aToken the access token
	 * @return a new <code>HTTP_Request</code> instance.
	 */
	HTTP_Request tokenAuth(String aToken);

	/**
	 * Sets the target address for this request.
	 * 
	 * @param aURL the target URL
	 * @return a new <code>HTTP_Request</code> instance.
	 * @throws HTTP_Exception if the URL scheme is not http or https
	 */
	HTTP_Request url(String aURL);
}