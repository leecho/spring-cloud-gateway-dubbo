package com.github.leecho.spring.cloud.dubbo.sample.provider;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @author LIQIU
 * @date 2021/6/28 17:20
 */
@Data
public class Request {

	private String message;

	private Target target;

	private Date date;

	private List<String> attributes;

	@Data
	public static class Target{
		private String id;
		private String name;
	}
}
