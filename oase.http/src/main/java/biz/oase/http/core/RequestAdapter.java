/* --------------------------------------------------------------------------
 * Project: Open Application Service Engine
 * 			CAR HTTP Client
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.http.core;

import static biz.oase.http.HEADER.AUTHORIZATION;
import static biz.oase.http.HEADER.CONTENT_TYPE;
import static biz.oase.http.core.VAL.Bearer;
import static biz.oase.http.core.VAL.DELETE;
import static biz.oase.http.core.VAL.GET;
import static biz.oase.http.core.VAL.PATCH;
import static biz.oase.http.core.VAL.POST;

import java.io.File;
import java.io.IOException;

import biz.oase.http.HTTP_Exception;
import biz.oase.http.HTTP_Request;
import biz.oase.http.HTTP_Response;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl.Builder;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Base implementation of the <code>HTTP_Request</code> interface.
 *
 * @version 2.0.0 04.03.2026 11:55:34
 */
public class RequestAdapter implements HTTP_Request {

	private static final OkHttpClient CLIENT;
	private static final String DEFAULT_CONTENT_TYPE;

	static {
		CLIENT = new OkHttpClient().newBuilder()
				.followRedirects(false)
				.followSslRedirects(false)
				.build();
		DEFAULT_CONTENT_TYPE = "text/plain"; //$NON-NLS-1$
	}

	private String contentType;
	private FormBody.Builder fb;
	private Headers.Builder hb;
	private RequestBody myBody;
	private String myMethod;
	private Request.Builder rb;

	/**
	 * Creates a default <code>RequestAdapter</code> instance.
	 */
	public RequestAdapter() {
		super();

		rb = new Request.Builder();
		hb = rb.getHeaders$okhttp();
		contentType = DEFAULT_CONTENT_TYPE;
	}

	@Override
	public HTTP_Request basicAuth(String aUser, String aPW) {
		String l_creds = Credentials.basic(aUser, aPW);

		return header(AUTHORIZATION, l_creds);
	}

	@Override
	public HTTP_Request body(String aBody) {
		MediaType l_mt = MediaType.parse(contentType);
		myBody = RequestBody.create(aBody, l_mt);

		return this;
	}

	@Override
	public HTTP_Request bodyFile(File aFile) {
		MediaType l_mt = MediaType.parse(contentType);
		myBody = RequestBody.create(aFile, l_mt);

		return this;
	}

	@Override
	public HTTP_Request contentType(String aType) {
		contentType = aType;

		return header(CONTENT_TYPE, aType);
	}

	/**
	 * Tells a request to execute an HTTP DELETE method.
	 * 
	 * @return the new <code>HTTP_Request</code> instance
	 */
	public HTTP_Request delete() {
		myMethod = DELETE;

		return this;
	}

	@Override
	public HTTP_Response execute() {
		try {
			if (myBody == null) {
				if (fb != null) {
					myBody = fb.build();
				}
			}
			rb = rb.method(myMethod, myBody);
			Request l_req = rb.build();
			Response l_response = CLIENT.newCall(l_req).execute();
			HTTP_Response l_ret = new ResponseAdapter(l_response);

			return l_ret;
		} catch (IOException anEx) {
			throw new HTTP_Exception(anEx);
		}
	}

	@Override
	public HTTP_Request formParameter(String aKey, String aValue) {
		if (fb == null) {
			fb = new FormBody.Builder();
		}
		fb = fb.add(aKey, aValue);

		return this;
	}

	/**
	 * Tells a request to execute an HTTP GET method.
	 * 
	 * @return the new <code>HTTP_Request</code> instance
	 */
	public HTTP_Request get() {
		myMethod = GET;

		return this;
	}

	@Override
	public HTTP_Request header(String aName, String aValue) {
		hb = hb.add(aName, aValue);

		return this;
	}

	@Override
	public HTTP_Request method(String aMethod) {
		myMethod = aMethod;
		
		return this;
	}

	/**
	 * Tells a request to execute an HTTP PATCH method.
	 * 
	 * @return the new <code>HTTP_Request</code> instance
	 */
	public HTTP_Request patch() {
		myMethod = PATCH;

		return this;
	}

	/**
	 * Tells a request to execute an HTTP POST method.
	 * 
	 * @return the new <code>HTTP_Request</code> instance
	 */
	public HTTP_Request post() {
		myMethod = POST;

		return this;
	}

	@Override
	public HTTP_Request queryParameter(String aKey, String aValue) {
		Builder l_url = rb.build().url().newBuilder();
		l_url = l_url.addQueryParameter(aKey, aValue);

		rb = rb.url(l_url.build());

		return this;
	}

	@Override
	public HTTP_Request tokenAuth(String aToken) {
		String l_creds = Bearer + " " + aToken; //$NON-NLS-1$

		return header(AUTHORIZATION, l_creds);
	}

	@Override
	public HTTP_Request url(String aURL) {
		rb = rb.url(aURL);

		return this;
	}
}
