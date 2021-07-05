package com.github.leecho.spring.cloud.gateway.dubbo.rewirte;

import lombok.Getter;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.web.server.ServerWebExchange;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LIQIU
 * @date 2021/7/2 16:48
 */
public class RewriteContext {

	@Getter
	private final ServerWebExchange exchange;

	@Getter
	private final Map<String, Object> parameters;

	@Getter
	private Map<String, Object> variables = new HashMap<>();

	public RewriteContext(ServerWebExchange exchange,
						 Map<String, Object> parameters) {
		this.exchange = exchange;
		this.parameters = parameters;
	}

	public void setVariable(String name, Object value) {
		variables.put(name, value);
	}

	public void setVariables(Map<String, Object> variables) {
		this.variables = variables;
	}
}
