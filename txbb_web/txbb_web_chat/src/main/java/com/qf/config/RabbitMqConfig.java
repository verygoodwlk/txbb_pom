package com.qf.config;

import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author ken
 * @Date 2019/2/22
 * @Version 1.0
 */
@Configuration
public class RabbitMqConfig {

    @Bean
    public Exchange getFanoutExchange(){
        return new FanoutExchange("msg_exchange");
    }
}
