package com.github.leecho.spring.cloud.gateway.dubbo.rewirte;


import com.github.leecho.spring.cloud.gateway.dubbo.rewirte.variable.render.VariableRender;
import com.github.leecho.spring.cloud.gateway.dubbo.rewirte.variable.process.VariableLoader;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

/**
 * @author LIQIU
 * @date 2021/7/1 17:45
 */
public class DefaultDubboArgumentRewriter implements DubboArgumentRewriter {

	private final VariableRender render ;

	private final VariableLoader processor;

	public DefaultDubboArgumentRewriter(VariableRender variableRender,
										VariableLoader variableLoader) {
		this.render = variableRender;
		this.processor = variableLoader;
	}


	@Override
	public Map<String, Object> rewrite(Map<String, Object> template, ServerWebExchange exchange, Map<String, Object> parameters) {
		RewriteContext rewriteContext = new RewriteContext(exchange, parameters);
		processor.load(rewriteContext);
		return this.render.render(template,rewriteContext);
	}
}
