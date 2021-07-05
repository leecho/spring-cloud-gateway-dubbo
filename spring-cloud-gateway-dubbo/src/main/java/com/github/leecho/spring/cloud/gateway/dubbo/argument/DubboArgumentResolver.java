package com.github.leecho.spring.cloud.gateway.dubbo.argument;

import com.github.leecho.spring.cloud.gateway.dubbo.rewirte.DubboArgumentRewriter;
import com.github.leecho.spring.cloud.gateway.dubbo.route.DubboRoute;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.web.server.ServerWebExchange;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LIQIU
 * @date 2021/7/5 9:06
 */
public class DubboArgumentResolver {

	private final DubboArgumentRewriter rewriter;

	private final DubboArgumentReader reader;

	public DubboArgumentResolver(DubboArgumentRewriter rewriter,
								 DubboArgumentReader reader) {
		this.rewriter = rewriter;
		this.reader = reader;
	}

	@SuppressWarnings({"unchecked"})
	public Map<String, Object> resolve(DubboRoute definition, ServerWebExchange exchange, DataBuffer body) {
		Map<String, Object> parameters = (Map<String, Object>) reader.read(exchange, body);
		if (parameters != null) {
			if (definition.getRewrite() != null && definition.getRewrite().size() > 0) {
				return rewriter.rewrite(definition.getRewrite(), exchange, parameters);
			} else {
				return parameters;
			}
		}
		return new HashMap<>(0);
	}

}
