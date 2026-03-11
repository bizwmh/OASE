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
import biz.oase.http.HTTP_RequestDTO;

/**
 * Defines the parameters for a HTTP request.
 *
 * @version 2.0.0 05.03.2026 07:43:12
 */
public interface RequestDTO {

	/**
	 * @return the new HTTP_Request based on the underlying request runtime
	 *         parameters
	 */
	static HTTP_Request newRequest(HTTP_RequestDTO aDTO) {
		RequestAdapter l_ret = new RequestAdapter();

		l_ret.method(aDTO.method);
		String l_url = aDTO.url;

		if (aDTO.resource != null) {
			l_url = l_url + "/" + aDTO.resource; //$NON-NLS-1$
		}
		l_ret.url(l_url);

		if (aDTO.header.size() > 0) {
			aDTO.header.entrySet().forEach(entry -> {
				String l_key = entry.getKey();
				String l_value = entry.getValue();

				l_ret.header(l_key, l_value);
			});
		}
		if (aDTO.queryParameter.size() > 0) {
			aDTO.queryParameter.entrySet().forEach(entry -> {
				String l_key = entry.getKey();
				String l_value = entry.getValue();

				l_ret.queryParameter(l_key, l_value);
			});
		}
		if (aDTO.formParameter.size() > 0) {
			aDTO.formParameter.entrySet().forEach(entry -> {
				String l_key = entry.getKey();
				String l_value = entry.getValue();

				l_ret.formParameter(l_key, l_value);
			});
		}
		l_ret.body(aDTO.body);
		
		return l_ret;
	}
}
