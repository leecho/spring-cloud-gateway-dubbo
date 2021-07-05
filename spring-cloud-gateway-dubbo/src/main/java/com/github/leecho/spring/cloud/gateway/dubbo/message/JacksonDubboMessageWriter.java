package com.github.leecho.spring.cloud.gateway.dubbo.message;

import lombok.Setter;
import org.springframework.core.ResolvableType;
import org.springframework.core.codec.Encoder;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.MimeTypeUtils;
import reactor.core.publisher.Mono;

/**
 * @author LIQIU
 * @date 2021/7/5 10:31
 */
public class JacksonDubboMessageWriter implements DubboMessageWriter {

	@Setter
	private Encoder<Object> encoder = new Jackson2JsonEncoder();

	@Override
	public Mono<Void> write(Object result, ServerHttpResponse response) {
		DataBuffer dataBuffer = encoder.encodeValue(result, response.bufferFactory(), ResolvableType.forClass(String.class), MimeTypeUtils.APPLICATION_JSON, null);
		response.getHeaders().add("Content-Type", MimeTypeUtils.APPLICATION_JSON_VALUE);
		Mono<Void> mono = response.writeWith(Mono.justOrEmpty(dataBuffer));
		DataBufferUtils.release(dataBuffer);
		return mono;
	}
}
