package com.ecommerce.gateway;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RouteController {
    @Bean
    public RouteLocator createRouteLocator(RouteLocatorBuilder builder){
        return builder.routes()
                .route("product-service", r -> r
                        .path("/api/v1/products/**")
                        .uri("lb://PRODUCT-SERVICE"))

                .route("user-service", r -> r
                        .path("/api/v1/users/**")
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
