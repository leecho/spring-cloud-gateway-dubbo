package com.github.leecho.spring.cloud.gateway.dubbo.route;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.web.server.ServerWebExchange;

/**
 * Dubbo路由断言，用于判断请求的路由是否是Dubbo路由
 * @author LIQIU
 * @date 2021/7/5 10:58
 */
public interface DubboRoutePredicate {

	/**
	 * 判断是否是Dubbo路由
	 * @param exchange exchange
	 * @param route gateway路由
	 * @return 是否为dubbo路由
	 */
	boolean test(ServerWebExchange exchange, Route route);
}
