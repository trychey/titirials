package com.baeldung.spring.cloud.bootstrap.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class GatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
            .route("book-service-route", p -> p.path("/book-service/**")
                .filters(f -> f.stripPrefix(1))
                .uri("lb://book-service"))
            .route("rating-service-route", p -> p.path("/rating-service/**")
                .filters(f -> f.rewritePath("/rating-service/(?<remaining>.*)", "/$\\{remaining}"))
                .uri("lb://rating-service"))
            .build();
    }
}
