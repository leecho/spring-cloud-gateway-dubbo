package com.github.leecho.spring.cloud.gateway.dubbo.argument;

import org.springframework.core.ResolvableType;
import org.springframework.core.codec.Decoder;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.server.ServerWebExchange;

import java.util.Map;

/**
 * @author LIQIU
 * @date 2021/7/5 10:37
 */
public class JacksonDubboArgumentReader implements DubboArgumentReader {

	private final Decoder<Object> decoder = new Jackson2JsonDecoder();

	private final MimeType jsonMimeType = MimeTypeUtils.APPLICATION_JSON;

	@Override
	public Object read(ServerWebExchange exchange, DataBuffer body) {
		return decoder.decode(body, ResolvableType.forType(Map.class), jsonMimeType, null);
	}
}
