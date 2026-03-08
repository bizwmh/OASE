/* --------------------------------------------------------------------------
 * Project: CAR HTTP Client
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.http;

import java.util.Map;
import java.util.function.Function;

import com.typesafe.config.Config;

import biz.car.config.XConfig;
import biz.oase.http.core.VAL;

/**
 * Defines the parameters for a HTTP request.
 *
 * @version 1.0.0 05.03.2026 07:43:12
 */
public class RequestDTO {

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
	 * Creates a default <code>RequestDTO</code> instance.
	 * 
	 * @param aConfig the configuration to use for building the DTO.
	 */
	public RequestDTO(XConfig aConfig) {
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
}
