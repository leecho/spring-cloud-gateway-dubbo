package com.github.leecho.spring.cloud.gateway.dubbo.argument.rewirte;


import com.github.leecho.spring.cloud.gateway.dubbo.argument.rewirte.variable.VariableRenderContext;
import com.github.leecho.spring.cloud.gateway.dubbo.argument.rewirte.variable.render.VariableRender;
import com.github.leecho.spring.cloud.gateway.dubbo.argument.rewirte.variable.loader.VariableLoader;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

/**
 * Dubbo 参数重写
 * @author LIQIU
 * @date 2021/7/1 17:45
 */
public class DefaultDubboArgumentRewriter implements DubboArgumentRewriter {

	/**
	 * 变量渲染器
	 */
	private final VariableRender render ;

	/**
	 * 变量加载器
	 */
	private final VariableLoader loader;

	public DefaultDubboArgumentRewriter(VariableRender variableRender,
										VariableLoader variableLoader) {
		this.render = variableRender;
		this.loader = variableLoader;
	}

	@Override
	public Map<String, Object> rewrite(Map<String, Object> template, ServerWebExchange exchange, Map<String, Object> parameters) {
		VariableRenderContext variableRenderContext = new VariableRenderContext(exchange, parameters);
		loader.load(variableRenderContext);
		return this.render.render(template, variableRenderContext);
	}
}
