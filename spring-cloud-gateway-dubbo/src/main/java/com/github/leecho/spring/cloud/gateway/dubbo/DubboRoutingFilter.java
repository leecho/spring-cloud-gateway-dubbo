package com.github.leecho.spring.cloud.gateway.dubbo;

import com.github.leecho.spring.cloud.gateway.dubbo.argument.DubboArgumentResolver;
import com.github.leecho.spring.cloud.gateway.dubbo.message.DubboMessageWriter;
import com.github.leecho.spring.cloud.gateway.dubbo.route.DubboRouteFactory;
import com.github.leecho.spring.cloud.gateway.dubbo.route.DubboRoutePredicate;
import com.github.leecho.spring.cloud.gateway.dubbo.client.DubboClient;
import com.github.leecho.spring.cloud.gateway.dubbo.message.DubboMessageConverter;
import com.github.leecho.spring.cloud.gateway.dubbo.route.DubboRoute;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author LIQIU
 * @date 2021/6/28 10:08
 */
@Slf4j
public class DubboRoutingFilter implements GlobalFilter, Ordered {

	/**
	 * Dubbo路由工厂
	 */
	private final DubboRouteFactory routeFactory;

	/**
	 * Dubbo 路由断言
	 */
	private final DubboRoutePredicate routePredicate;

	/**
	 * Dubbo客户端
	 */
	public final DubboClient dubboClient;

	/**
	 * Dubbo结果输出
	 */
	private final DubboMessageWriter writer;

	/**
	 * Dubbo结果转换
	 */
	private final DubboMessageConverter converter;

	/**
	 * Dubbo参数处理
	 */
	private final DubboArgumentResolver resolver;

	public DubboRoutingFilter(DubboRouteFactory routeFactory,
							  DubboRoutePredicate routePredicate,
							  DubboClient dubboClient,
							  DubboMessageWriter writer,
							  DubboArgumentResolver resolver,
							  DubboMessageConverter converter) {
		this.routeFactory = routeFactory;
		this.routePredicate = routePredicate;
		this.dubboClient = dubboClient;
		this.writer = writer;
		this.resolver = resolver;
		this.converter = converter;
		log.info("Dubbo routing filter initialized");
	}

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		Route route = exchange.getRequiredAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
		boolean matched = routePredicate.test(exchange, route);
		if (!matched) {
			return chain.filter(exchange);
		}

		exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_ALREADY_ROUTED_ATTR, true);

		ServerHttpResponse response = exchange.getResponse();
		DubboRoute dubboRoute = routeFactory.get(route);

		return DataBufferUtils.join(exchange.getRequest().getBody())
				.map(dataBuffer -> resolver.resolve(dubboRoute, exchange, dataBuffer))
				.map(parameters -> dubboClient.invoke(dubboRoute, parameters))
				.flatMap(Mono::fromFuture)
				.map(this::convert)
				.flatMap(result -> this.writer.write(result, response));
	}

	/**
	 * 转换Dubbo结果
	 *
	 * @param result 结果
	 * @return 转换后的结果
	 */
	private Object convert(Object result) {
		return this.converter != null ? this.converter.convert(result) : result;
	}


	@Override
	public int getOrder() {
		return 9999;
	}
}
