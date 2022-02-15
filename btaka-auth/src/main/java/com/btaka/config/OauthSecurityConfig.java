package com.btaka.config;

import com.btaka.jwt.JwtService;
import com.btaka.security.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import reactor.core.publisher.Mono;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class OauthSecurityConfig extends AuthorizationServerConfigurerAdapter {

    private final JwtService jwtService;

    private final ReactiveAuthenticationManager reactiveAuthenticationManager;

    private final ServerSecurityContextRepository securityContextRepository;

    protected List<String> excludeUrl() {
        return List.of("");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity security) {
        return security
                .exceptionHandling(exceptionHandlingSpec -> {
                    exceptionHandlingSpec
                            .authenticationEntryPoint(((exchange, ex) -> Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED))))
                            .accessDeniedHandler((exchange, denied) -> Mono.fromRunnable(() -> exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN)));
                })
                .cors().disable()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authenticationManager(reactiveAuthenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange(exchange -> {
                    exchange.pathMatchers("/api/v1/auth/**", "/api/v1/join/**")
                            .permitAll()
                            .anyExchange()
                            .authenticated();
                })
                .addFilterAt(new JwtAuthenticationFilter(jwtService, excludeUrl()), SecurityWebFiltersOrder.HTTP_BASIC)
                .build();
    }

}
