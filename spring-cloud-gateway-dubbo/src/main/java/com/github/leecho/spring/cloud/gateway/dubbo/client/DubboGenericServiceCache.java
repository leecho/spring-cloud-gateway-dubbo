package com.github.leecho.spring.cloud.gateway.dubbo.client;

import com.github.leecho.spring.cloud.gateway.dubbo.route.DubboRoute.DubboInterface;
import org.apache.dubbo.config.spring.ReferenceBean;
import org.apache.dubbo.rpc.service.GenericService;

/**
 * @author LIQIU
 * @date 2021/7/1 15:27
 */
public interface DubboGenericServiceCache {

	ReferenceBean<GenericService> get(DubboInterface dubboInterface);

	void put(DubboInterface dubboInterface, ReferenceBean<GenericService> referenceBean);

}
