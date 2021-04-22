package org.mql.spring.gatewayservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.ReactiveDiscoveryClient;
import org.springframework.cloud.gateway.discovery.DiscoveryClientRouteDefinitionLocator;
import org.springframework.cloud.gateway.discovery.DiscoveryLocatorProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableHystrix
public class GatewayServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayServiceApplication.class, args);
    }

    @Bean
    public RouteLocator getStaticRoute(RouteLocatorBuilder locatorBuilder) {
        return locatorBuilder.routes()
                .route(route ->
                        route.path("/countries/**").filters(f ->
                                f.addRequestHeader("x-rapidapi-key", "dad68dcdfamshbdd7c93282ea5d7p1250ecjsn21f704180b06")
                                        .addRequestHeader("x-rapidapi-host", "ajayakv-rest-countries-v1.p.rapidapi.com")
                                        .addRequestHeader("useQueryString", "true")
                                        .rewritePath("/countries/(?<segment>.*)", "/${segment}")
                                        .hystrix(h->h.setName("countries").setFallbackUri("forward:/defaultCountries"))
                        )
                                .uri("https://ajayakv-rest-countries-v1.p.rapidapi.com").id("r1"))
                .route(route ->
                        route.path("/muslim/**").filters(f ->
                                f.addRequestHeader("x-rapidapi-key", "dad68dcdfamshbdd7c93282ea5d7p1250ecjsn21f704180b06")
                                        .addRequestHeader("x-rapidapi-host", "muslimsalat.p.rapidapi.com")
                                        .addRequestHeader("useQueryString", "true")
                                        .rewritePath("/muslim/(?<segment>.*)", "/${segment}")
                        )
                                .uri("https://muslimsalat.p.rapidapi.com").id("r2"))
                .build();
    }

    @Bean
    public DiscoveryClientRouteDefinitionLocator getDynamicRoute(ReactiveDiscoveryClient discoveryClient
            , DiscoveryLocatorProperties discoveryLocatorProperties) {
        return new DiscoveryClientRouteDefinitionLocator(discoveryClient, discoveryLocatorProperties);
    }

}
