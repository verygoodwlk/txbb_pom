package com.qf.txbb_web_friends;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(scanBasePackages = "com.qf")
@EnableEurekaClient
@MapperScan("com.qf.dao")
@EnableFeignClients("com.qf")
public class TxbbWebFriendsApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxbbWebFriendsApplication.class, args);
    }

}
