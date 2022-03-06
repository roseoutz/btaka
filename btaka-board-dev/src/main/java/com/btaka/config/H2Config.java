package com.btaka.config;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.sql.SQLException;

@Component
@Profile("test")
public class H2Config {

    @Value("${server.port:13000}")
    private int serverPort;

    private Server h2Server;

    @EventListener(ContextRefreshedEvent.class)
    public void start() throws SQLException {
        this.h2Server = org.h2.tools.Server.createWebServer("-webPort", serverPort + 1 + "", "-tcpAllowOthers");
        this.h2Server.start();
    }

    @EventListener(ContextClosedEvent.class)
    public void stop() {
        if (h2Server != null) this.h2Server.stop();
    }
}
