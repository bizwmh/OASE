/* --------------------------------------------------------------------------
 * Project: CAR HTTP Client
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.http;

import java.util.Map;

import com.typesafe.config.Config;

import biz.car.config.CConfig;

/**
 * Defines the parameters for a HTTP request.
 *
 * @version 2.0.0 11.03.2026 08:44:16
 */
public class HTTP_RequestDTO extends CConfig {

	public String body;
	public Map<String, String> formParameter;
	public Map<String, String> header;
	public String method;
	public Map<String, String> queryParameter;
	public String resource;
	public String url;

	/**
	 * Creates a default <code>HTTP_RequestDTO</code> instance.
	 */
	public HTTP_RequestDTO() {
		super();
	}

	/**
	 * Creates a <code>HTTP_RequestDTO</code> instance.
	 * 
	 * @param aConfig the associated request configuration
	 */
	public HTTP_RequestDTO(Config aConfig) {
		super(aConfig);
	}
}
