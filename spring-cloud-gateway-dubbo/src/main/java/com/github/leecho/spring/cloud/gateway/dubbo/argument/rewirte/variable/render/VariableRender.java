package com.github.leecho.spring.cloud.gateway.dubbo.argument.rewirte.variable.render;

import com.github.leecho.spring.cloud.gateway.dubbo.argument.rewirte.variable.VariableRenderContext;

import java.util.Map;

/**
 * 变量渲染器
 * @author LIQIU
 * @date 2021/7/2 16:58
 */
public interface VariableRender {

	/**
	 * 将参数中的占位符渲染成为变量值
	 * @param arguments 参数
	 * @param context 上下文
	 * @return 结果
	 */
	Map<String,Object> render(Map<String,Object> arguments , VariableRenderContext context);

}
