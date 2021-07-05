package com.github.leecho.spring.cloud.gateway.dubbo.argument.rewirte.variable.loader;

import com.github.leecho.spring.cloud.gateway.dubbo.argument.rewirte.variable.VariableRenderContext;
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
	public void load(VariableRenderContext context) {
		Map<String, String> headers = context.getExchange()
				.getRequest()
				.getHeaders()
				.entrySet()
				.stream()
				.collect(Collectors.toMap(Map.Entry::getKey, entry -> String.join(",", entry.getValue())));
		context.setVariable(name,headers);
	}
}
