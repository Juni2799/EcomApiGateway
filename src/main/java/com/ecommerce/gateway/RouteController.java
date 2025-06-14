package com.ecommerce.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class RouteController {
    @Bean
    public RouteLocator createRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("product-service", r -> r
                        .path("/api/v1/products/**")
                        .filters(f -> f.retry(retryConfig ->
                                        retryConfig.setRetries(5)
                                                .setTimeout(Duration.ofMillis(500)))  //Use .setMethods to define Retry for specific http requests
                                .circuitBreaker(config ->
                                config.setName("ecomBreaker")
                                        .setFallbackUri("forward:/api/v1/fallback/product")))
                        .uri("lb://PRODUCT-SERVICE"))

                .route("user-service", r -> r
                        .path("/api/v1/users/**")
                        .filters(f -> f.retry(retryConfig ->
                                        retryConfig.setRetries(5)
                                                .setTimeout(Duration.ofMillis(500)))
                                .circuitBreaker(config ->
                                config.setName("ecomBreaker")
                                        .setFallbackUri("forward:/api/v1/fallback/user")))
                        .uri("lb://USER-SERVICE"))

                .route("order-service", r -> r
                        .path("/api/v1/orders/**", "/api/v1/cart/**")
                        .uri("lb://ORDER-SERVICE"))

                .route("eureka-server", r -> r
                        .path("/eureka/main")
                        .filters(f -> f.rewritePath("/eureka/main", "/"))
                        .uri("http://localhost:8761"))

                .route("eureka-static-server", r -> r
                        .path("/eureka/**")
                        .uri("http://localhost:8761"))
                .build();
    }
}
