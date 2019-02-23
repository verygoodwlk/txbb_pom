package com.qf.txbb_web_chat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication(scanBasePackages = "com.qf")
@EnableEurekaClient
@MapperScan("com.qf.dao")
public class TxbbWebChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxbbWebChatApplication.class, args);
    }

}
