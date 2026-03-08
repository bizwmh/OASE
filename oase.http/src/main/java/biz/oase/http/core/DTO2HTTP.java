/* --------------------------------------------------------------------------
 * Project: CAR HTTP Client
 * --------------------------------------------------------------------------
 * Use of this software is subject to license terms. All Rights Reserved. 
 * -------------------------------------------------------------------------- */

package biz.oase.http.core;

import java.util.function.Supplier;

import biz.oase.http.HTTP_Request;
import biz.oase.http.RequestDTO;

/**
 * Creates a HTTP request based on a RequestDTO.
 *
 * @version 1.0.0 05.03.2026 08:45:16
 */
@FunctionalInterface
public interface DTO2HTTP extends Supplier<RequestDTO> {

	default HTTP_Request newRequest() {
		RequestDTO l_dto = get();
		RequestAdapter l_ret = new RequestAdapter();

		l_ret.method(l_dto.method);
		String l_url = l_dto.url;

		if (l_dto.resource != null) {
			l_url = l_url + "/" + l_dto.resource; //$NON-NLS-1$
		}
		l_ret.url(l_url);

		if (l_dto.header != null) {
			l_dto.header.entrySet().forEach(entry -> {
				String l_key = entry.getKey();
				String l_value = entry.getValue();
				
				l_ret.header(l_key, l_value);
			});
		}
		if (l_dto.queryParameter != null) {
			l_dto.queryParameter.entrySet().forEach(entry -> {
				String l_key = entry.getKey();
				String l_value = entry.getValue();
				
				l_ret.queryParameter(l_key, l_value);
			});
		}
		if (l_dto.formParameter != null) {
			l_dto.formParameter.entrySet().forEach(entry -> {
				String l_key = entry.getKey();
				String l_value = entry.getValue();
				
				l_ret.formParameter(l_key, l_value);
			});
		}
		if (l_dto.body != null) {
			l_ret.body(l_dto.body);
		}
		return l_ret;
	}
}
