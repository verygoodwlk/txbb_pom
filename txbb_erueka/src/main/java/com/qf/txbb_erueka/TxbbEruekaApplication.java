package com.qf.txbb_erueka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class TxbbEruekaApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxbbEruekaApplication.class, args);
    }

}

