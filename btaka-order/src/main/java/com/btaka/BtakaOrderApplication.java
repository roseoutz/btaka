package com.btaka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class BtakaOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(BtakaOrderApplication.class, args);
    }

}
