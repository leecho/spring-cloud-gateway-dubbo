package com.github.leecho.spring.cloud.dubbo.sample.gateway;

import com.github.leecho.spring.cloud.gateway.dubbo.client.DubboGenericServiceCache;
import com.github.leecho.spring.cloud.gateway.dubbo.route.DubboRoute;
import com.google.common.cache.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.ReferenceBean;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.service.GenericService;

import java.time.Duration;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * @author LIQIU
 * @date 2021/7/1 15:33
 * FIXME: 2021/7/6 该缓存还存在问题，1、无法有效销毁Consumer，2、过期策略未触发
 */
@Slf4j
public class GuavaDubboGenericServiceCache implements DubboGenericServiceCache {

	private final LoadingCache<String, Optional<ReferenceBean<GenericService>>> genericServiceCache;

	public GuavaDubboGenericServiceCache(int maxClientSize, Duration keepAlive) {
		genericServiceCache = CacheBuilder.newBuilder()
				.expireAfterAccess(keepAlive)
				.maximumSize(maxClientSize)
				.removalListener((RemovalListener<String, Optional<ReferenceBean<GenericService>>>) removalNotification -> {
					removalNotification.getValue().ifPresent(referenceBean -> {
						if (log.isDebugEnabled()) {
							log.debug("Destroy dubbo genericService {}", referenceBean.getInterface());
						}
						ReferenceConfigCache.getCache().destroy(referenceBean);
					});
				})
				.build(new CacheLoader<String, Optional<ReferenceBean<GenericService>>>() {
					@Override
					public Optional<ReferenceBean<GenericService>> load(String s) {
						return Optional.empty();
					}
				});
	}

	@Override
	public ReferenceBean<GenericService> get(DubboRoute.DubboInterface dubboInterface) {
		try {
			Optional<ReferenceBean<GenericService>> optional = genericServiceCache.get(dubboInterface.toString());
			return optional.orElse(null);
		} catch (ExecutionException e) {
			throw new RpcException(" Load genericService fail :", e);
		}
	}

	@Override
	public void put(DubboRoute.DubboInterface dubboInterface, ReferenceBean<GenericService> referenceBean) {
		genericServiceCache.put(dubboInterface.toString(), Optional.of(referenceBean));
	}
}
