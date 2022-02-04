package com.btaka.security.filter;

import org.springframework.http.HttpMethod;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

public class JwtAuthenticationFilter implements WebFilter {

    private final List<String> excludeUrl;

    public JwtAuthenticationFilter(List<String> excludeUrl) {
        this.excludeUrl = excludeUrl;
    }

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
