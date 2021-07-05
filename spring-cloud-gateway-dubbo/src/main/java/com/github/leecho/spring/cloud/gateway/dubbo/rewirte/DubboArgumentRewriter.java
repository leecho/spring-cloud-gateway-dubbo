package com.github.leecho.spring.cloud.gateway.dubbo.rewirte;

import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

/**
 * @author LIQIU
 * @date 2021/7/2 17:14
 */
public interface DubboArgumentRewriter {
	/**
	 * @param template
	 * @param exchange
	 * @param parameters
	 * @return
	 */
	Map<String,Object> rewrite(Map<String,Object> template, ServerWebExchange exchange,Map<String,Object> parameters) ;

}
