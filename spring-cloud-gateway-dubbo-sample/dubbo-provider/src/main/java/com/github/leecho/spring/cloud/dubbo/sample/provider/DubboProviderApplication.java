package com.github.leecho.spring.cloud.dubbo.sample.provider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author LIQIU
 * @date 2021/6/30 18:11
 */
@EnableDubbo
@EnableDiscoveryClient
@SpringBootApplication
public class DubboProviderApplication {

	public static void main(String[] args) {
		SpringApplication.run(DubboProviderApplication.class,args);
	}

}
