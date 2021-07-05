package com.github.leecho.spring.cloud.gateway.dubbo.argument.rewirte.variable.loader;

import com.github.leecho.spring.cloud.gateway.dubbo.argument.rewirte.variable.VariableRenderContext;
import org.springframework.http.HttpCookie;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author LIQIU
 * @date 2021/7/2 19:33
 */
public class CookieVariableLoader implements VariableLoader {

	@Override
	public void load(VariableRenderContext context) {
		Map<String, String> cookies = context.getExchange()
				.getRequest()
				.getCookies()
				.entrySet()
				.stream()
				.collect(Collectors.toMap(Map.Entry::getKey, entry ->
						entry.getValue()
								.stream()
								.map(HttpCookie::getValue)
								.collect(Collectors.joining(","))));
		context.setVariable("cookie", cookies);
	}

}
