package com.btaka.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

@DependsOn("servicesConfig")
@Configuration
public class GwConfig {

    private final ServicesConfig servicesConfig;

    public GwConfig(ServicesConfig servicesConfig) {
        this.servicesConfig = servicesConfig;
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder locatorBuilder) {
        return locatorBuilder.routes()
                .route("path_route", r -> r.path("/")
                            .filters(f -> f.addRequestHeader("hello", "world")
                                    .rewritePath("/", "/api/v1/user/list")
                            ).uri(servicesConfig.getAuthService())
                ).build();
    }
}
