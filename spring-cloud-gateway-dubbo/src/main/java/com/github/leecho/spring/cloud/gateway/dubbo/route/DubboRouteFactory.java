package com.github.leecho.spring.cloud.gateway.dubbo.route;

import org.springframework.cloud.gateway.route.Route;

/**
 * Dubbo 路由工厂，用于构建Dubbo路由
 * @author LIQIU
 * @date 2021/7/5 10:54
 */
public interface DubboRouteFactory {

	/**
	 * 获取Dubbo路由
	 * @param route Gateway路由
	 * @return Dubbo路由
	 */
	DubboRoute get(Route route);

}
