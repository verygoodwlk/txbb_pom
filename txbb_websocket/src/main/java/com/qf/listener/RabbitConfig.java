package com.qf.listener;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author ken
 * @Date 2019/2/22
 * @Version 1.0
 */
@Configuration
public class RabbitConfig {

    @Value("${ws.ip}")
    private String ip;
    @Value("${ws.port}")
    private int port;

    /**
     * 创建一个队列
     * @return
     */
    @Bean
    public Queue getQueue(){
        return new Queue("msg_queue_" + ip + ":" + port);
    }

    @Bean
    public Queue getQueueChat(){
        return new Queue("chat_queue");
    }

    /**
     * 创建路由
     * @return
     */
    @Bean
    public FanoutExchange getFanoutExchange(){
        return new FanoutExchange("msg_exchange");
    }

    /**
     * 绑定路由和队列
     * @return
     */
    @Bean
    public Binding binding(FanoutExchange getFanoutExchange, Queue getQueue){
        return BindingBuilder.bind(getQueue).to(getFanoutExchange);
    }
}
