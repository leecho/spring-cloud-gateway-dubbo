package com.github.leecho.spring.cloud.gateway.dubbo.rewirte.variable.render;

import com.github.leecho.spring.cloud.gateway.dubbo.rewirte.RewriteContext;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author LIQIU
 * @date 2021/7/2 18:25
 */
class VelocityVariableRenderTest {

	private VelocityVariableRender rewriterRender = new VelocityVariableRender();

	@Test
	void render() {
		Map<String, Object> arguments = new HashMap<>();
		arguments.put("name", "$header.name");

		List<Object> values = new ArrayList<>();
		values.add("$query.value");
		values.add("$query.value");
		Map<String, Object> subValues = new HashMap<>();
		subValues.put("svalue1", "$query.value");
		subValues.put("svalue2", "$body.text");
		values.add(subValues);
		arguments.put("values", values);

		arguments.put("value2", "$query.value");
		arguments.put("body", "$body.text");
		arguments.put("array",new String[]{"$query.param","$body.foo"});

		RewriteContext rewriteContext = new RewriteContext(null,null);
		Map<String, Object> header = new HashMap<>();
		header.put("name", "name1");
		rewriteContext.setVariable("header",header);
		Map<String, Object> query = new HashMap<>();
		query.put("value", "value1");
		query.put("param", "param1");

		rewriteContext.setVariable("query",query);

		Map<String, Object> body = new HashMap<>();
		body.put("text", "body1");
		body.put("foo", "bar");
		rewriteContext.setVariable("body",body);


		Map<String, Object> result = rewriterRender.render(arguments, rewriteContext);
		System.out.println(result);

	}

	@Test
	void renderSample() {
		Map<String, Object> template = new HashMap<>();
		template.put("name", "$.header.name");

		RewriteContext rewriteContext = new RewriteContext(null, null);
		Map<String, Object> header = new HashMap<>();
		header.put("name", "name1");
		List<String> query = new ArrayList<>();
		query.add("abc");
		rewriteContext.setVariable("header",header);
		rewriteContext.setVariable("query",query);

		Map<String, Object> result = rewriterRender.render(template, rewriteContext);
		System.out.println(result);

	}
}