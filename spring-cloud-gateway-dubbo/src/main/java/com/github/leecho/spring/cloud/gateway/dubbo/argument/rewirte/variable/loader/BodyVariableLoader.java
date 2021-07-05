package com.github.leecho.spring.cloud.gateway.dubbo.argument.rewirte.variable.loader;

import com.github.leecho.spring.cloud.gateway.dubbo.argument.rewirte.variable.VariableRenderContext;
import lombok.Getter;
import lombok.Setter;

/**
 * @author LIQIU
 * @date 2021/7/2 16:33
 */
public class BodyVariableLoader implements VariableLoader {

	@Getter
	@Setter
	private String name = "body";

	@Override
	public void load(VariableRenderContext context) {
		context.setVariable(name, context.getParameters());
	}
}
