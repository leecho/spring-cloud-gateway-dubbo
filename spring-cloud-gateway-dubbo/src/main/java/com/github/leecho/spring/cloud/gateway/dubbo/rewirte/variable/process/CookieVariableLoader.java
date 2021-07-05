package com.github.leecho.spring.cloud.gateway.dubbo.rewirte.variable.process;

import com.github.leecho.spring.cloud.gateway.dubbo.rewirte.RewriteContext;
import org.springframework.http.HttpCookie;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author LIQIU
 * @date 2021/7/2 19:33
 */
public class CookieVariableLoader implements VariableLoader {

	@Override
	public void load(RewriteContext context) {
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
