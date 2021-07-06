package com.github.leecho.spring.cloud.gateway.dubbo;

import com.github.leecho.spring.cloud.gateway.dubbo.route.DefaultDubboRouteFactory;
import com.github.leecho.spring.cloud.gateway.dubbo.route.DubboRoute;
import org.junit.jupiter.api.Test;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.web.server.ServerWebExchange;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author LIQIU
 * @date 2021/6/30 17:23
 */
class DubboRouteTest {

	private DefaultDubboRouteFactory dubboRouteFactory = new DefaultDubboRouteFactory();

	@Test
	void testSimple() throws URISyntaxException {
		Map<String,Object> dubboMetadata = new HashMap<>();
		dubboMetadata.put(DubboRoute.PARAMETER_TYPES_KEY, Arrays.asList("com.github.spring.cloud.dubbo.sample.provider.EchoRequest","java.lang.String"));
		dubboMetadata.put("group","default");
		dubboMetadata.put("version","0.0.1");

		Route route = Route.async()
				.id("route")
				.uri(new URI("dubbo://dubbo-provider/com.jczh.auth.resource.dubbo.EchoService/request"))
				.metadata("dubbo", dubboMetadata)
				.asyncPredicate(exchange -> null).build();


		DubboRoute dubboRoute = dubboRouteFactory.get(route);
		DubboRoute.DubboInterface dubboInterface = dubboRoute.getDubboInterface();
		assertEquals(dubboInterface.getServiceName(),"dubbo-provider");
		assertEquals(dubboInterface.getInterfaceClass(),"com.jczh.auth.resource.dubbo.EchoService");
		assertEquals(dubboInterface.getGroup(),"default");
		assertEquals(dubboInterface.getVersion(),"0.0.1");
		assertEquals(dubboRoute.getMethod(),"request");
	}

	@Test
	void testMultipleParameters() throws URISyntaxException {
		Predicate<ServerWebExchange> predicate = exchange -> false;
		Route route = Route.builder()
				.id("route")
				.and(predicate)
				.uri(new URI("dubbo://dubbo-provider/com.jczh.auth.resource.dubbo.EchoService:default:0.0.1/request"))
				.metadata(DubboRoute.PARAMETER_TYPES_KEY, Arrays.asList("com.github.spring.cloud.dubbo.sample.provider.EchoRequest","java.lang.String"))
				.build();
		DubboRoute dubboRoute = dubboRouteFactory.get(route);
		assertEquals(dubboRoute.getParameterTypes().length,2);
	}

	@Test
	void testMultipleQuery() throws URISyntaxException {
		Predicate<ServerWebExchange> predicate = exchange -> false;
		Route route = Route.builder()
				.id("route")
				.and(predicate)
				.uri(new URI("dubbo://dubbo-provider/com.jczh.auth.resource.dubbo.EchoService:default:0.0.1/request?parameterTypes=java.lang.String&p=2"))
				.metadata(DubboRoute.PARAMETER_TYPES_KEY, Arrays.asList("com.github.spring.cloud.dubbo.sample.provider.EchoRequest","java.lang.String"))
				.build();
		DubboRoute dubboRoute = dubboRouteFactory.get(route);
		assertEquals(dubboRoute.getParameterTypes().length,1);
	}

	@Test
	public void testDubboInterfaceEquals() throws URISyntaxException {
		Predicate<ServerWebExchange> predicate = exchange -> false;
		Route route = Route.builder()
				.id("route")
				.and(predicate)
				.uri(new URI("dubbo://dubbo-provider/com.jczh.auth.resource.dubbo.EchoService:default:0.0.1/request"))
				.build();
		Route route2 = Route.builder()
				.id("route")
				.and(predicate)
				.uri(new URI("dubbo://dubbo-provider/com.jczh.auth.resource.dubbo.EchoService:default:0.0.1/echo"))
				.build();
		DubboRoute dubboRoute = dubboRouteFactory.get(route);
		DubboRoute dubboRoute1 = dubboRouteFactory.get(route2);
		assertEquals(dubboRoute.getDubboInterface(), dubboRoute1.getDubboInterface());

	}
}