package com.github.leecho.spring.cloud.gateway.dubbo.starter;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author LIQIU
 * @date 2021/7/2 16:41
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({DubboRoutingAutoConfiguration.class})
public @interface EnableDubboRouting {

}
