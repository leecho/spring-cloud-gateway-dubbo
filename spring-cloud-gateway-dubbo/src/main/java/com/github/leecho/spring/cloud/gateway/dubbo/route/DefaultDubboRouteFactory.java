package com.github.leecho.spring.cloud.gateway.dubbo.route;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import org.apache.dubbo.common.utils.CollectionUtils;
import org.springframework.cloud.gateway.route.Route;

import java.net.URI;
import java.util.Collection;
import java.util.Map;

/**
 * @author LIQIU
 * @date 2021/7/5 10:54
 */
public class DefaultDubboRouteFactory implements DubboRouteFactory {

	/**
	 * Dubbo路由缓存
	 */
	private final Table<String, Integer, DubboRoute> dubboRouteTable = HashBasedTable.create();

	@Override
	public DubboRoute get(Route route) {
		Map<Integer, DubboRoute> row = dubboRouteTable.row(route.getId());
		DubboRoute existsRoute = row.entrySet()
				.stream()
				.filter(entry -> entry.getKey().equals(route.hashCode()))
				.map(Map.Entry::getValue)
				.findAny()
				.orElse(null);
		if (existsRoute != null) {
			return existsRoute;
		} else {
			//删除冗余缓存
			if (CollectionUtils.isNotEmptyMap(row)) {
				row.keySet().forEach(hashCode -> dubboRouteTable.remove(route.getId(), hashCode));
			}
		}
		DubboRoute dubboRoute = this.build(route);
		dubboRouteTable.put(route.getId(), route.hashCode(), dubboRoute);
		return dubboRoute;
	}

	/**
	 * 通过URI构建调用对象
	 * dubbo://service-name/interface/method
	 *
	 * @param route 网关路由
	 * @return Dubbo调用对象
	 */
	@SuppressWarnings({"unchecked"})
	private DubboRoute build(Route route) {

		URI uri = route.getUri();
		String serviceName = uri.getHost();
		String path = uri.getPath();
		if (path.startsWith("/")) {
			path = path.substring(1);
		}
		String[] parts = path.split("/");
		String interfaceClass = parts[0];
		String method = parts[1];

		Object dubboMetadata = route.getMetadata().get("dubbo");
		if (dubboMetadata == null) {
			return new DubboRoute(serviceName, interfaceClass, method);
		}

		Map<String, Object> dubboMetadataMap = (Map<String, Object>) dubboMetadata;

		String[] parameterTypes = new String[]{};
		Object parameterTypesMetadata = dubboMetadataMap.get(DubboRoute.PARAMETER_TYPES_KEY);
		if (parameterTypesMetadata != null) {
			Collection<String> parameterTypeList = ((Map<String, String>) parameterTypesMetadata).values();
			parameterTypes = parameterTypeList.toArray(new String[]{});
		}

		String group = String.valueOf(dubboMetadataMap.get("group"));
		String version = String.valueOf(dubboMetadataMap.get("version"));

		Map<String, Object> rewrite = null;
		Object rewriteMetadata = dubboMetadataMap.get("rewrite");
		if (rewriteMetadata != null) {
			rewrite = (Map<String, Object>) rewriteMetadata;
		}

		return new DubboRoute(serviceName, interfaceClass, group, version, method, parameterTypes, rewrite);
	}
}
