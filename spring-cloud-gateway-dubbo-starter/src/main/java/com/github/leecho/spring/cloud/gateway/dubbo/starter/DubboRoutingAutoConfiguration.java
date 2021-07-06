package com.github.leecho.spring.cloud.gateway.dubbo.starter;

import com.github.leecho.spring.cloud.gateway.dubbo.argument.DubboArgumentReader;
import com.github.leecho.spring.cloud.gateway.dubbo.argument.DubboArgumentResolver;
import com.github.leecho.spring.cloud.gateway.dubbo.argument.JacksonDubboArgumentReader;
import com.github.leecho.spring.cloud.gateway.dubbo.message.DubboMessageConverter;
import com.github.leecho.spring.cloud.gateway.dubbo.DubboRoutingFilter;
import com.github.leecho.spring.cloud.gateway.dubbo.client.DubboClient;
import com.github.leecho.spring.cloud.gateway.dubbo.client.DefaultDubboClient;
import com.github.leecho.spring.cloud.gateway.dubbo.client.DubboGenericServiceCache;
import com.github.leecho.spring.cloud.gateway.dubbo.message.DubboMessageWriter;
import com.github.leecho.spring.cloud.gateway.dubbo.message.JacksonDubboMessageWriter;
import com.github.leecho.spring.cloud.gateway.dubbo.argument.rewirte.DefaultDubboArgumentRewriter;
import com.github.leecho.spring.cloud.gateway.dubbo.argument.rewirte.DubboArgumentRewriter;
import com.github.leecho.spring.cloud.gateway.dubbo.argument.rewirte.variable.loader.*;
import com.github.leecho.spring.cloud.gateway.dubbo.argument.rewirte.variable.render.VariableRender;
import com.github.leecho.spring.cloud.gateway.dubbo.argument.rewirte.variable.render.SpelVariableRender;
import com.github.leecho.spring.cloud.gateway.dubbo.argument.rewirte.variable.render.VelocityVariableRender;
import com.github.leecho.spring.cloud.gateway.dubbo.route.DefaultDubboRouteFactory;
import com.github.leecho.spring.cloud.gateway.dubbo.route.DubboRouteFactory;
import com.github.leecho.spring.cloud.gateway.dubbo.route.DubboRoutePredicate;
import com.github.leecho.spring.cloud.gateway.dubbo.route.DubboRouteUriSchemePredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gateway.config.conditional.ConditionalOnEnabledGlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LIQIU
 * @date 2021/7/2 16:39
 */
@Configuration
@EnableConfigurationProperties(DubboRoutingProperties.class)
public class DubboRoutingAutoConfiguration {


	@Bean
	@ConditionalOnEnabledGlobalFilter
	public DubboRoutingFilter dubboRoutingFilter(DubboRouteFactory routeFactory,
												 DubboRoutePredicate routePredicate,
												 DubboClient dubboClient,
												 @Autowired(required = false) DubboMessageConverter converter,
												 DubboArgumentResolver resolver,
												 DubboMessageWriter writer) {
		return new DubboRoutingFilter(routeFactory, routePredicate, dubboClient, writer, resolver, converter);
	}

	@Bean
	@ConditionalOnMissingBean(DubboClient.class)
	public DubboClient dubboClient(@Autowired(required = false) DubboGenericServiceCache genericServiceCache,
								   DubboRoutingProperties dubboRoutingProperties) {
		DefaultDubboClient defaultDubboClient = new DefaultDubboClient(genericServiceCache);
		defaultDubboClient.setInvokeAsync(dubboRoutingProperties.getInvokeAsync());
		return defaultDubboClient;
	}

	@Bean
	@ConditionalOnMissingBean(DubboMessageWriter.class)
	public DubboMessageWriter dubboMessageWriter() {
		return new JacksonDubboMessageWriter();
	}

	@Bean
	public DubboArgumentResolver dubboArgumentResolver(DubboArgumentRewriter rewriter,
													   DubboArgumentReader dubboArgumentReader) {
		return new DubboArgumentResolver(rewriter, dubboArgumentReader);
	}

	@Bean
	@ConditionalOnMissingBean(DubboRouteFactory.class)
	public DubboRouteFactory dubboRouteFactory() {
		return new DefaultDubboRouteFactory();
	}

	@Bean
	@ConditionalOnMissingBean(DubboRoutePredicate.class)
	public DubboRoutePredicate dubboRoutePredicate() {
		return new DubboRouteUriSchemePredicate();
	}

	@Bean
	@ConditionalOnMissingBean(DubboArgumentReader.class)
	public DubboArgumentReader dubboArgumentReader() {
		return new JacksonDubboArgumentReader();
	}

	@Bean
	public DelegateVariableLoader variableProcessor(List<VariableLoader> loaders) {
		if (loaders == null) {
			loaders = new ArrayList<>();
		}
		loaders.add(new BodyVariableLoader());
		loaders.add(new HeaderVariableLoader());
		loaders.add(new QueryVariableLoader());
		loaders.add(new CookieVariableLoader());
		return new DelegateVariableLoader(loaders);
	}

	@Bean
	@ConditionalOnMissingBean(DubboArgumentRewriter.class)
	public DubboArgumentRewriter dubboArgumentRewriter(VariableRender variableRender,
													   DelegateVariableLoader delegateVariableProcessor) {
		return new DefaultDubboArgumentRewriter(variableRender, delegateVariableProcessor);
	}

	@Bean
	@ConditionalOnProperty(prefix = DubboRoutingProperties.PREFIX, name = "rewrite-render", matchIfMissing = true)
	@ConditionalOnMissingBean(VariableRender.class)
	public VariableRender spelRewriterRender() {
		return new SpelVariableRender();
	}

	@Bean
	@ConditionalOnProperty(prefix = DubboRoutingProperties.PREFIX, name = "rewrite-render", havingValue = "velocity")
	@ConditionalOnMissingBean(VariableRender.class)
	public VariableRender velocityRewriterRender() {
		return new VelocityVariableRender();
	}
}
