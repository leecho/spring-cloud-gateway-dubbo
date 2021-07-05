package com.github.leecho.spring.cloud.gateway.dubbo.rewirte.variable.render;

import com.github.leecho.spring.cloud.gateway.dubbo.rewirte.RewriteContext;

import java.util.Map;

/**
 * @author LIQIU
 * @date 2021/7/2 16:58
 */
public interface VariableRender {

	Map<String,Object> render(Map<String,Object> arguments , RewriteContext context);

}
