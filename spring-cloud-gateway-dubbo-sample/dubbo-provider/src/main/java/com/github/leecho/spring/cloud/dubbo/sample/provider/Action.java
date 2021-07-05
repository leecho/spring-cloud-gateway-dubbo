package com.github.leecho.spring.cloud.dubbo.sample.provider;

import lombok.Data;

import java.util.Map;

/**
 * @author LIQIU
 * @date 2021/7/1 10:22
 */
@Data
public class Action {
	private String name;
	private Map<String,String> parameters;
}
