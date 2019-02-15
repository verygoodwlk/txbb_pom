package com.qf.txbb_web_resource;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Import;

@SpringBootApplication(scanBasePackages = "com.qf")
@EnableEurekaClient
@Import(FdfsClientConfig.class)
@EnableFeignClients(basePackages = "com.qf")
public class TxbbWebResourceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TxbbWebResourceApplication.class, args);
    }

}

