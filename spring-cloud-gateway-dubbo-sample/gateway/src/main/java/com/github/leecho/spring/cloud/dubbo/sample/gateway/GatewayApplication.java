package com.github.leecho.spring.cloud.dubbo.sample.gateway;


import com.github.leecho.spring.cloud.gateway.dubbo.starter.EnableDubboRouting;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 *
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableDubboRouting
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class,args);
	}

}
