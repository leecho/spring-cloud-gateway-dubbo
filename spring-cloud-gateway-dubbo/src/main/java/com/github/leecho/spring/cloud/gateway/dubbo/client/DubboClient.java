package com.github.leecho.spring.cloud.gateway.dubbo.client;

import com.github.leecho.spring.cloud.gateway.dubbo.route.DubboRoute;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * Dubbo 调用客户端
 * @author LIQIU
 * @date 2021/6/30 14:55
 */
public interface DubboClient {

	/**
	 * 调用Dubbo接口，返回异步对象
	 * @param invocation Dubbo调用
	 * @param parameters 参数
	 * @return 结果
	 */
	Mono<Object> invoke(DubboRoute invocation, Map<String, Object> parameters);
}
