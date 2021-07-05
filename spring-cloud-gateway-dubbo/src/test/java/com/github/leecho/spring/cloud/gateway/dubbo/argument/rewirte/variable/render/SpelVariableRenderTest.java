package com.github.leecho.spring.cloud.gateway.dubbo.argument.rewirte.variable.render;

import com.github.leecho.spring.cloud.gateway.dubbo.argument.rewirte.variable.VariableRenderContext;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @author LIQIU
 * @date 2021/7/2 17:18
 */
class SpelVariableRenderTest {

	@Test
	void render() {
		SpelVariableRender variableRender = new SpelVariableRender();
		Map<String, Object> arguments = new HashMap<>();
		arguments.put("name", "#header['name']");

		List<Object> values = new ArrayList<>();
		values.add("#query['value']");
		values.add("#query['value']");
		Map<String, Object> subValues = new HashMap<>();
		subValues.put("svalue1", "#query['value']");
		subValues.put("svalue2", "#body['text']");
		values.add(subValues);
		arguments.put("values", values);

		arguments.put("value2", "#query['value']");
		arguments.put("body", "#body['text']");
		arguments.put("array",new String[]{"#query['param']","#body['foo']"});

		VariableRenderContext variableRenderContext = new VariableRenderContext(null, null);
		Map<String, Object> header = new HashMap<>();
		header.put("name", "name1");
		variableRenderContext.setVariable("header",header);
		Map<String, Object> query = new HashMap<>();
		query.put("value", "value1");
		query.put("param", "param1");

		variableRenderContext.setVariable("query",query);

		Map<String, Object> body = new HashMap<>();
		body.put("text", "body1");
		body.put("foo", "bar");
		variableRenderContext.setVariable("body",body);


		Map<String, Object> result = variableRender.render(arguments, variableRenderContext);
		System.out.println(result);

	}

	@Test
	void renderSample() {
		SpelVariableRender variableRender = new SpelVariableRender();
		Map<String, Object> template = new HashMap<>();
		template.put("name", "#header['name']");

		VariableRenderContext variableRenderContext = new VariableRenderContext(null, null);
		Map<String, Object> header = new HashMap<>();
		header.put("name", "name1");
		List<String> query = new ArrayList<>();
		query.add("abc");
		variableRenderContext.setVariable("header",header);
		variableRenderContext.setVariable("query",query);

		Map<String, Object> result = variableRender.render(template, variableRenderContext);
		System.out.println(result);

	}
}