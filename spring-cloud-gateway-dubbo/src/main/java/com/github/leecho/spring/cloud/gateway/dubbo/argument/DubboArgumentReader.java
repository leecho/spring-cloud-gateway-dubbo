
package com.github.leecho.spring.cloud.gateway.dubbo.argument;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author LIQIU
 * @date 2021/7/5 10:36
 */
public interface DubboArgumentReader {


	/**
	 * 读取请求入参
	 * @param exchange 请求
	 * @param body     请求体内容
	 * @return 请求内容
	 */
	Object read(ServerWebExchange exchange, DataBuffer body);

}
