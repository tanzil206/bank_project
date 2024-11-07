package com.bank.api_gateway.Config;


import com.bank.api_gateway.Filter.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    private final JwtAuthenticationFilter filter;

    public GatewayConfig(JwtAuthenticationFilter filter) {
        this.filter = filter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes().route("Authentication", r -> r.path("/auth/**").filters(f -> f.filter(filter)).uri("http://localhost:8082"))

                .route("Account", r -> r.path("/accounts/**").filters(f -> f.filter(filter)).uri("http://localhost:8083"))

                .build();
    }
}