package com.github.leecho.spring.cloud.gateway.dubbo.argument.rewirte.variable.loader;

import com.github.leecho.spring.cloud.gateway.dubbo.argument.rewirte.variable.VariableRenderContext;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @author LIQIU
 * @date 2021/7/2 16:27
 */
@Slf4j
public class DelegateVariableLoader implements VariableLoader {

	private final List<VariableLoader> loaders;

	public DelegateVariableLoader(List<VariableLoader> loaders) {
		this.loaders = loaders;
		if(log.isInfoEnabled()){
			log.info("Dubbo rewrite variable loaders initialized , loaders: {}", loaders);
		}
	}

	@Override
	public void load(VariableRenderContext context) {
		loaders.forEach(loader -> loader.load(context));
	}
}
