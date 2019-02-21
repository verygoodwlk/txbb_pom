package com.qf.config;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @Author ken
 * @Date 2019/2/21
 * @Version 1.0
 */
@Configuration
public class ZkConfig {

    @Value("${zk.host}")
    private String zkHost;

    @Bean
    public ZooKeeper createZookeeper(){

        ZooKeeper zooKeeper = null;
        try {
            zooKeeper = new ZooKeeper(zkHost, 60, new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return zooKeeper;
    }
}
