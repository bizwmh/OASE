/* --------------------------------------------------------------------------
 * Project: CAR HTTP Client
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.http;

import java.util.HashMap;
import java.util.Map;

import com.typesafe.config.Config;

import biz.car.config.CConfig;
import biz.car.config.XConfig;
import biz.oase.http.core.VAL;

/**
 * Defines the parameters for a HTTP request.
 *
 * @version 2.0.0 11.03.2026 08:44:16
 */
public class HTTP_RequestDTO {

	public String body;
	public Map<String, String> formParameter;
	public Map<String, String> header;
	public String method;
	public Map<String, String> queryParameter;
	public String resource;
	public String url;

	/**
	 * Creates a <code>HTTP_RequestDTO</code> instance.
	 * 
	 * @param aConfig the associated request configuration
	 */
	public HTTP_RequestDTO(Config aConfig) {
		super();

		XConfig l_conf = new CConfig(aConfig);
		method = l_conf.getString(VAL.method, ""); //$NON-NLS-1$
		url = l_conf.getString(VAL.url, ""); //$NON-NLS-1$
		resource = l_conf.getString(VAL.resource, ""); //$NON-NLS-1$
		header = toMap(VAL.header, l_conf);
		queryParameter = toMap(VAL.queryParameter, l_conf);
		formParameter = toMap(VAL.formParameter, l_conf);
		body = l_conf.getString(VAL.body, null);
	}

	private Map<String, String> toMap(String aKey, XConfig aConfig) {
		Map<String, String> l_ret = new HashMap<String, String>();

		if (aConfig.hasConfig(aKey)) {
			Config l_conf = aConfig.config().getConfig(aKey);
			l_ret = XConfig.toStringMap(l_conf);
			
			for (String l_key : l_ret.keySet()) {
				if (l_key.startsWith("\"")) { //$NON-NLS-1$
					String l_value = l_ret.remove(l_key);
					l_key = l_key.replaceAll("^\"|\"$", ""); //$NON-NLS-1$ //$NON-NLS-2$
					
					l_ret.put(l_key, l_value);
				}
			}
		}
		return l_ret;
	}
}
