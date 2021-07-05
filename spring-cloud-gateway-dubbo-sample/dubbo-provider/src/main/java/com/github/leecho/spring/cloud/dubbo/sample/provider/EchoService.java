package com.github.leecho.spring.cloud.dubbo.sample.provider;

import java.util.Map;

/**
 * @author LIQIU
 * created on 2021/4/9
 **/
public interface EchoService {

    /**
     * @param message
     * @return
     */
    String echo(String message);

    Map<String, Object> request(Request request, Action action);
}
