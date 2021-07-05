package com.github.leecho.spring.cloud.gateway.dubbo.client;

import com.github.leecho.spring.cloud.gateway.dubbo.route.DubboRoute;
import com.github.leecho.spring.cloud.gateway.dubbo.route.DubboRoute.DubboInterface;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.ReferenceBean;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.service.GenericService;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author LIQIU
 * @date 2021/6/30 14:55
 */
@Slf4j
public class DefaultDubboClient implements DubboClient {

	private final DubboGenericServiceCache dubboGenericServiceCache;

	public DefaultDubboClient(DubboGenericServiceCache dubboGenericServiceCache) {
		this.dubboGenericServiceCache = dubboGenericServiceCache;
	}

	@Override
	public CompletableFuture<Object> invoke(DubboRoute invocation, Map<String, Object> parameters) {
		try {
			log.info("Invoke dubbo service , parameters: {}", parameters);
			GenericService genericService = this.loadGenericService(invocation.getDubboInterface());
			CompletableFuture<Object> result = genericService.$invokeAsync(invocation.getMethod(), invocation.getParameterTypes(), parameters.values().toArray(new Object[]{}));
			log.info("Invoke dubbo service success, result: {}", result);
			return result;
		} catch (Throwable e) {
			log.error("Invoke dubbo fail:", e);
			throw new RpcException(e.getMessage(), e);
		}
	}

	/**
	 * 获取Dubbo泛化调用服务
	 *
	 * @param dubboInterface
	 * @return
	 */
	private GenericService loadGenericService(DubboInterface dubboInterface) {
		try {
			GenericService genericService;
			if (this.dubboGenericServiceCache != null) {
				ReferenceBean<GenericService> referenceBean = this.dubboGenericServiceCache.get(dubboInterface);
				if (referenceBean == null) {
					referenceBean = this.buildReferenceBean(dubboInterface);
					dubboGenericServiceCache.put(dubboInterface, referenceBean);
				}
				genericService = referenceBean.get();
			} else {
				return this.buildReferenceBean(dubboInterface).get();
			}
			return genericService;
		} catch (Exception e) {
			throw new RpcException("load dubbo genericService fail", e);
		}
	}

	private ReferenceBean<GenericService> buildReferenceBean(DubboInterface dubboInterface) {
		ReferenceBean<GenericService> referenceBean = new ReferenceBean<>();
		referenceBean.setGeneric(true);
		referenceBean.setInterface(dubboInterface.getInterfaceClass());
		referenceBean.setVersion(dubboInterface.getVersion());
		referenceBean.setGroup(dubboInterface.getGroup());
		referenceBean.setCheck(false);
		referenceBean.setRetries(0);
		referenceBean.setAsync(true);
		return referenceBean;
	}
}
