/* --------------------------------------------------------------------------
 * Project: CAR HTTP Client
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.http.core;

import java.util.Map;
import java.util.function.Function;

import com.typesafe.config.Config;

import biz.car.config.XConfig;
import biz.oase.http.HTTP_Request;

/**
 * Defines the parameters for a HTTP request.
 *
 * @version 1.0.0 05.03.2026 07:43:12
 */
public class RequestConfig {

	public final String body;
	public final Map<String, String> formParameter;
	public final Map<String, String> header;
	public final String method;
	public final Map<String, String> queryParameter;
	public final String resource;
	public final String url;

	private XConfig conf;
	private Function<String, Map<String, String>> ToMap = field -> {
		if (conf.hasConfig(field)) {
			Config l_conf = conf.config().getConfig(field);

			return XConfig.toStringMap(l_conf);
		}
		return null;
	};

	/**
	 * Creates a <code>RequestConfig</code> instance.
	 * 
	 * @param aConfig the configuration to use for building the DTO.
	 */
	public RequestConfig(XConfig aConfig) {
		super();

		conf = aConfig;
		method = conf.getString(VAL.method, ""); //$NON-NLS-1$
		url = conf.getString(VAL.url, ""); //$NON-NLS-1$
		resource = conf.getString(VAL.resource, ""); //$NON-NLS-1$
		header = ToMap.apply(VAL.header);
		queryParameter = ToMap.apply(VAL.queryParameter);
		formParameter = ToMap.apply(VAL.formParameter);
		body = conf.getString(VAL.body, null);
	}

	/**
	 * TODO newRequest
	 * 
	 * @return
	 */
	public HTTP_Request newRequest() {
		RequestAdapter l_ret = new RequestAdapter();

		l_ret.method(method);
		String l_url = url;

		if (resource != null) {
			l_url = l_url + "/" + resource; //$NON-NLS-1$
		}
		l_ret.url(l_url);

		if (header != null) {
			header.entrySet().forEach(entry -> {
				String l_key = entry.getKey();
				String l_value = entry.getValue();

				l_ret.header(l_key, l_value);
			});
		}
		if (queryParameter != null) {
			queryParameter.entrySet().forEach(entry -> {
				String l_key = entry.getKey();
				String l_value = entry.getValue();

				l_ret.queryParameter(l_key, l_value);
			});
		}
		if (formParameter != null) {
			formParameter.entrySet().forEach(entry -> {
				String l_key = entry.getKey();
				String l_value = entry.getValue();

				l_ret.formParameter(l_key, l_value);
			});
		}
		// TODO body
		return l_ret;
	}
}
