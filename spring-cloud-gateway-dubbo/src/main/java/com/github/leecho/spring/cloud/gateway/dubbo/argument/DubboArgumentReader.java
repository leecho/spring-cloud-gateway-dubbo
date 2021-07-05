
package com.github.leecho.spring.cloud.gateway.dubbo.argument;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.web.server.ServerWebExchange;

/**
 * @author LIQIU
 * @date 2021/7/5 10:36
 */
public interface DubboArgumentReader {


	/**
	 * @param exchange
	 * @param body
	 * @return
	 */
	Object read(ServerWebExchange exchange, DataBuffer body);

}
