package com.github.leecho.spring.cloud.gateway.dubbo.starter;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author LIQIU
 * @date 2021/7/2 18:45
 */
@Getter
@Setter
@ConfigurationProperties(DubboRoutingProperties.PREFIX)
public class DubboRoutingProperties {

	public final static String PREFIX = "spring.cloud.gateway.dubbo";

	private Client client;

	private Rewrite rewrite;

	@Getter
	@Setter
	public static class Client{
		private Boolean invokeAsync;
	}

	@Getter
	@Setter
	public static class Rewrite{
		private String render;
	}
}
