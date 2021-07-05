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

	private final Map<String,Object> rewrite;

	public static final String PARAMETER_TYPES_KEY = "parameterTypes";

	private DubboRoute(String serviceName,
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

	/**
	 * 通过URI构建调用对象
	 * dubbo://service-name/interface:group:version/method?parameterTypes=
	 *
	 * @param route 网关路由
	 * @return Dubbo调用对象
	 */
	@SuppressWarnings({"rawtype"})
	public static DubboRoute of(Route route) {
		URI uri = route.getUri();
		String path = uri.getPath();
		if (path.startsWith("/")) {
			path = path.substring(1);
		}
		String[] parts = path.split("/");
		String interfaceClass = parts[0];
		String method = parts[1];

		String group = null;
		String version = null;
		String[] parameterTypes = new String[]{};
		Map<String, Object> rewrite = null;

		Object dubboMetadata = route.getMetadata().get("dubbo");
		if (dubboMetadata != null) {
			Map<String,Object> dubboMetadataMap = (Map<String, Object>)dubboMetadata;
			Object parameterTypesMetadata = dubboMetadataMap.get(PARAMETER_TYPES_KEY);
			if (parameterTypesMetadata != null) {
				Collection<String> parameterTypeList = ((Map<String, String>) parameterTypesMetadata).values();
				parameterTypes = parameterTypeList.toArray(new String[]{});
			}
			Object groupMetadata = dubboMetadataMap.get("group");
			Object versionMetadata = dubboMetadataMap.get("version");

			Object rewriteMetadata = dubboMetadataMap.get("rewrite");
			if(rewriteMetadata != null){
				rewrite = (Map<String, Object>) rewriteMetadata;
			}

			if (groupMetadata != null) {
				group = groupMetadata.toString();
			}

			if (versionMetadata != null) {
				version = versionMetadata.toString();
			}

		}


		return new DubboRoute(uri.getHost(), interfaceClass, group, version, method, parameterTypes,rewrite);
	}

	@Getter
	public static class DubboInterface {
		private final String serviceName;
		private final String interfaceClass;
		private final String group;
		private final String version;

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
