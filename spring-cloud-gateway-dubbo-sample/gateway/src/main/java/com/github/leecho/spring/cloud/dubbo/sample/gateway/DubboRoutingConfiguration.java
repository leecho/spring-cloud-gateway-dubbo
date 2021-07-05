package com.github.leecho.spring.cloud.dubbo.sample.gateway;

import com.github.leecho.spring.cloud.gateway.dubbo.message.DubboMessageConverter;
import com.github.leecho.spring.cloud.gateway.dubbo.client.DubboGenericServiceCache;
import com.github.leecho.spring.cloud.gateway.dubbo.rewirte.variable.process.VariableLoader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LIQIU
 * @date 2021/6/28 10:17
 */
@Configuration
public class DubboRoutingConfiguration {

	@Bean
	public DubboGenericServiceCache dubboGenericServiceCache() {
		return new GuavaDubboGenericServiceCache(1000, Duration.of(10, ChronoUnit.SECONDS));
	}

	@Bean
	public DubboMessageConverter dubboResultConverter() {
		return source -> {
			Map<String, Object> result = new HashMap<>(2);
			result.put("success", true);
			result.put("data", source);
			return result;
		};
	}

	@Bean
	public VariableLoader authVariableProcessor() {
		return context -> {
			Map<String, Object> auth = new HashMap<>();
			auth.put("user", "user");
			context.setVariable("auth", auth);
		};
	}

}
