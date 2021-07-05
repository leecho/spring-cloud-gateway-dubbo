package com.github.leecho.spring.cloud.gateway.dubbo.message;

/**
 * Dubbo 结果转换器
 * @author LIQIU
 * @date 2021/7/1 10:36
 */
public interface DubboMessageConverter {

	/**
	 * 转换Dubbo结果
	 * @param source
	 * @return
	 */
	Object convert(Object source);

}
