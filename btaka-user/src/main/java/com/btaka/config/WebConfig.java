package com.btaka.config;

import com.btaka.common.config.AbstractWebConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;

@Configuration
@EnableWebFlux
public class WebConfig extends AbstractWebConfig {
}
