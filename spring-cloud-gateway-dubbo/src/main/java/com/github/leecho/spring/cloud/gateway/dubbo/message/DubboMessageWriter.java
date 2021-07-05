package com.github.leecho.spring.cloud.gateway.dubbo.message;

import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

/**
 * Dubbo 消息写组件
 * @author LIQIU
 * @date 2021/7/1 12:03
 */
public interface DubboMessageWriter {

	/**
	 * 将结果写入response，返回调用端
	 * @param result 执行结果
	 * @param response response
	 * @return 写入结果
	 */
	Mono<Void> write(Object result, ServerHttpResponse response);

}
