package com.github.leecho.spring.cloud.gateway.dubbo.rewirte.variable.process;

import com.github.leecho.spring.cloud.gateway.dubbo.rewirte.RewriteContext;
import lombok.Getter;
import lombok.Setter;

/**
 * @author LIQIU
 * @date 2021/7/2 16:31
 */
public class QueryVariableLoader implements VariableLoader {

	@Getter
	@Setter
	private String name = "query";

	@Override
	public void load(RewriteContext context) {
		context.setVariable(name, context.getExchange().getRequest().getQueryParams());
	}
}
