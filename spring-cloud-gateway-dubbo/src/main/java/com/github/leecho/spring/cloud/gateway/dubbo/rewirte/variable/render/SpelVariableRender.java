package com.github.leecho.spring.cloud.gateway.dubbo.rewirte.variable.render;

import com.github.leecho.spring.cloud.gateway.dubbo.rewirte.RewriteContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author LIQIU
 * @date 2021/7/2 16:59
 */
@Slf4j
public class SpelVariableRender implements VariableRender {

	private final ExpressionParser parser = new SpelExpressionParser();

	@Override
	public Map<String, Object> render(Map<String, Object> template, RewriteContext rewriteContext) {
		EvaluationContext context = new StandardEvaluationContext();
		rewriteContext.getVariables()
				.forEach(context::setVariable);
		return this.parseMap(parser, template, context);
	}

	/**
	 * @param parser
	 * @param map
	 * @param context
	 * @return
	 */
	private Map<String, Object> parseMap(ExpressionParser parser, Map<String, Object> map, EvaluationContext context) {
		Map<String, Object> result = new HashMap<>(map.size());
		map.forEach((key, value) -> result.put(key, this.parse(parser, value, context)));
		return result;
	}

	/**
	 * @param parser
	 * @param collection
	 * @param context
	 * @return
	 */
	private Collection<Object> parseCollection(ExpressionParser parser, Collection<Object> collection, EvaluationContext context) {
		return collection.stream()
				.map(value -> this.parse(parser, value, context))
				.collect(Collectors.toList());
	}

	/**
	 * @param parser
	 * @param array
	 * @param context
	 * @return
	 */
	private Object[] parseArray(ExpressionParser parser, Object[] array, EvaluationContext context) {
		return Stream.of(array)
				.map(value -> this.parse(parser, value, context))
				.toArray();
	}


	/**
	 * @param parser
	 * @param target
	 * @param context
	 * @return
	 */
	private Object parse(ExpressionParser parser, Object target, EvaluationContext context) {
		if (target instanceof Map) {
			return this.parseMap(parser, (Map<String, Object>) target, context);
		} else if (target instanceof Collection) {
			return this.parseCollection(parser, (Collection) target, context);
		} else if (target.getClass().isArray()) {
			return this.parseArray(parser, (Object[]) target, context);
		} else if (target.getClass().equals(String.class)) {
			String expression = target.toString();
			if (expression.startsWith("#")) {
				return parse(parser, target.toString(), context);
			}
			return target;
		} else {
			return target;
		}
	}

	/**
	 * @param parser
	 * @param expression
	 * @param context
	 * @return
	 */
	private Object parse(ExpressionParser parser, String expression, EvaluationContext context) {
		Expression expression1 = parser.parseExpression(expression);
		Object value = expression1.getValue(context);
		log.debug("parse expression: {}, result: {}", expression, value);
		if (value != null && value.toString().equals(expression)) {
			return null;
		}
		return value;
	}
}
