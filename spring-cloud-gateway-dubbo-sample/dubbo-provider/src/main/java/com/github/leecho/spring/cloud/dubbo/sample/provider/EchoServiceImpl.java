package com.github.leecho.spring.cloud.dubbo.sample.provider;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LIQIU
 * created on 2021/4/9
 **/
@Slf4j
@DubboService(group = "default",version = "1.0")
public class EchoServiceImpl implements EchoService {

	@Override
	public String echo(String message) {
		log.info("echo : {}", message);
		return "echo :" + message;
	}

	@Override
	public Map<String, Object> request(Request request, Action action) {
		log.info("request : {}, action:{}", request, action);
		HashMap<String, Object> result = new HashMap<>();
		result.put("request", request);
		result.put("action", action);
		return result;
	}
}
