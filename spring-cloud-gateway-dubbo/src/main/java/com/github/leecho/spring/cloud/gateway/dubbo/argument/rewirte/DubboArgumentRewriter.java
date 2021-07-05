package com.github.leecho.spring.cloud.gateway.dubbo.argument.rewirte;

import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

/**
 * @author LIQIU
 * @date 2021/7/2 17:14
 */
public interface DubboArgumentRewriter {
	/**
	 * 重写dubbo参数
	 * @param template 模板
	 * @param exchange exchange
	 * @param parameters 入参
	 * @return 参数
	 */
	Map<String,Object> rewrite(Map<String,Object> template, ServerWebExchange exchange,Map<String,Object> parameters) ;

}
