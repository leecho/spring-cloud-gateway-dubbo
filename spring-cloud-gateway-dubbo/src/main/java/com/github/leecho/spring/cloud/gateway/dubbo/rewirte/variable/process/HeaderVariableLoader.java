package com.github.leecho.spring.cloud.gateway.dubbo.rewirte.variable.process;

import com.github.leecho.spring.cloud.gateway.dubbo.rewirte.RewriteContext;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author LIQIU
 * @date 2021/7/2 16:30
 */
public class HeaderVariableLoader implements VariableLoader {

	@Getter
	@Setter
	private String name = "header";


	@Override
	public void load(RewriteContext context) {
		Map<String, String> headers = context.getExchange()
				.getRequest()
				.getHeaders()
				.entrySet()
				.stream()
				.collect(Collectors.toMap(Map.Entry::getKey, entry -> String.join(",", entry.getValue())));
		context.setVariable(name,headers);
	}
}
