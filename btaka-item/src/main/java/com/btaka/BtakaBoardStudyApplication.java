package com.btaka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class BtakaBoardStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(BtakaBoardStudyApplication.class, args);
    }

}
