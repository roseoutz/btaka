package com.btaka.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@RequiredArgsConstructor
@Configuration
@EntityScan(basePackages = {"com.btaka.domain"})
@EnableJpaRepositories(basePackages = {"com.btaka.domain.study.repo", "com.btaka.domain.free.repo"})
public class JPAConfig {

}
