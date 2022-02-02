package com.btaka.security.filter;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpMethod;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Arrays;
import java.util.List;

public class JwtAuthencationFilter implements WebFilter {

    private final List<String> excludeUrl = Arrays.asList("/", "/login");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        HttpMethod httpMethod = exchange.getRequest().getMethod();
        URI requestUri = exchange.getRequest().getURI();

        if (HttpMethod.GET.equals(httpMethod) && excludeUrl.contains(requestUri.toString())) {
            return chain.filter(exchange);
        }

        return null;
    }
}
