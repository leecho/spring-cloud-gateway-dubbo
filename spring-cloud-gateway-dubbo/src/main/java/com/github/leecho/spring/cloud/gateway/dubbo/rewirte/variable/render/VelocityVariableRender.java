package com.github.leecho.spring.cloud.gateway.dubbo.rewirte.variable.render;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.leecho.spring.cloud.gateway.dubbo.rewirte.RewriteContext;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LIQIU
 * @date 2021/7/2 17:01
 */
public class VelocityVariableRender implements VariableRender {

	private final ObjectMapper objectMapper = new ObjectMapper();

	private final VelocityEngine velocityEngine = new VelocityEngine();

	public VelocityVariableRender() {
		velocityEngine.init();
	}

	@Override
	public HashMap<String, Object> render(Map<String, Object> arguments, RewriteContext context) {
		VelocityContext velocityContext = new VelocityContext();
		context.getVariables().forEach(velocityContext::put);
		String content;
		try {
			content = objectMapper.writeValueAsString(arguments);
		} catch (JsonProcessingException e) {
			throw new RewriteRenderException("encode template to json error", e);
		}
		StringWriter stringWriter = new StringWriter();
		velocityEngine.evaluate(velocityContext,stringWriter,"",content);
		String result = stringWriter.toString();
		try {
			return objectMapper.readValue(result, new TypeReference<HashMap<String, Object>>() {
			});
		} catch (JsonProcessingException e) {
			throw new RewriteRenderException("decode json to map error", e);
		}
	}
}
