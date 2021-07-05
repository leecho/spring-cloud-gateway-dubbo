package com.github.leecho.spring.cloud.gateway.dubbo.route;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.GATEWAY_SCHEME_PREFIX_ATTR;

/**
 * @author LIQIU
 * @date 2021/7/5 11:07
 */
public class DubboRouteUriSchemePredicate implements DubboRoutePredicate {
	@Override
	public boolean test(ServerWebExchange exchange, Route route) {
		URI url = route.getUri();
		String schemePrefix = exchange.getAttribute(GATEWAY_SCHEME_PREFIX_ATTR);
		if (url == null || (!"dubbo".equals(url.getScheme()) && !"dubbo".equals(schemePrefix))) {
			return false;
		}
		return true;
	}
}
