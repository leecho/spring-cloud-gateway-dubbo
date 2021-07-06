package com.github.leecho.spring.cloud.gateway.dubbo.route;


import lombok.Getter;
import org.springframework.cloud.gateway.route.Route;

import java.net.URI;
import java.util.*;

/**
 * Dubbo调用对象
 *
 * @author LIQIU
 * @date 2021/6/30 14:56
 */

@Getter
public class DubboRoute {

	/**
	 * 调用方法
	 */
	private final String method;

	/**
	 * 参数类型
	 */
	private final String[] parameterTypes;

	/**
	 * Dubbo接口
	 */
	private final DubboInterface dubboInterface;

	private final Map<String, Object> rewrite;

	public static final String PARAMETER_TYPES_KEY = "parameterTypes";

	public DubboRoute(String serviceName,
					  String interfaceClass,
					  String method) {
		this.dubboInterface = new DubboInterface(serviceName, interfaceClass);
		this.method = method;
		this.parameterTypes = new String[]{};
		this.rewrite = new HashMap<>(0);
	}

	public DubboRoute(String serviceName,
					  String interfaceClass,
					  String group,
					  String version,
					  String method,
					  String[] parameterTypes,
					  Map<String, Object> rewrite) {
		this.parameterTypes = parameterTypes;
		this.rewrite = rewrite;
		this.dubboInterface = new DubboInterface(serviceName, interfaceClass, group, version);
		this.method = method;
	}

	@Getter
	public static class DubboInterface {
		private final String serviceName;
		private final String interfaceClass;
		private final String group;
		private final String version;

		private DubboInterface(String serviceName, String interfaceClass) {
			this.serviceName = serviceName;
			this.interfaceClass = interfaceClass;
			this.group = null;
			this.version = null;
		}

		private DubboInterface(String serviceName, String interfaceClass, String group, String version) {
			this.serviceName = serviceName;
			this.interfaceClass = interfaceClass;
			this.group = group;
			this.version = version;
		}

		@Override
		public String toString() {

			StringBuilder builder = new StringBuilder()
					.append(serviceName)
					.append("/")
					.append(interfaceClass);

			if (group != null) {
				builder.append(":")
						.append(group);
			}
			if (version != null) {
				builder.append(":")
						.append(version);
			}

			return builder.toString();
		}
	}

}
