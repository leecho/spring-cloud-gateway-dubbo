package com.github.leecho.spring.cloud.gateway.dubbo.starter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author LIQIU
 * @date 2021/7/2 18:45
 */
@Getter
@Setter
@ConfigurationProperties(DubboRoutingProperties.PREFIX)
public class DubboRoutingProperties {

	public final static String PREFIX = "spring.cloud.gateway.dubbo";

	private String rewriteRender;

	private Boolean invokeAsync;

	// TODO: 2021/7/5 自定义内置参数加载器，暂未实现
	private Object variables ;
}
