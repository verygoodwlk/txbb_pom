package com.qf.txbb_web_chat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = "com.qf")
@EnableEurekaClient
public class TxbbWebChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxbbWebChatApplication.class, args);
    }

}
