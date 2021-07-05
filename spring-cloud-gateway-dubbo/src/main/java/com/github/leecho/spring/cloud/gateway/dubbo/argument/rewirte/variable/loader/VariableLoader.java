package com.github.leecho.spring.cloud.gateway.dubbo.argument.rewirte.variable.loader;

import com.github.leecho.spring.cloud.gateway.dubbo.argument.rewirte.variable.VariableRenderContext;

/**
 * 重写参数变量加载器
 * @author LIQIU
 * @date 2021/7/2 16:20
 */
public interface VariableLoader {

	/**
	 * 加载变量
	 * @param context 参数重写上下文
	 */
	void load(VariableRenderContext context);

}
