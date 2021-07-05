package com.github.leecho.spring.cloud.gateway.dubbo.route;

import org.springframework.cloud.gateway.route.Route;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author LIQIU
 * @date 2021/7/5 10:54
 */
public class DefaultDubboRouteFactory implements DubboRouteFactory {

	private Map<String, DubboRoute> dubboRouteCache = new ConcurrentHashMap<>();

	@Override
	public DubboRoute get(Route route) {
		DubboRoute existsRoute = dubboRouteCache.get(route.getId());
		if (existsRoute != null) {
			return existsRoute;
		}
		DubboRoute dubboRoute = DubboRoute.of(route);
		dubboRouteCache.put(route.getId(), dubboRoute);
		return dubboRoute;
	}
}
