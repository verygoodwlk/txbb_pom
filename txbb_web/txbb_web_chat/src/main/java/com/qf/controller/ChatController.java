package com.qf.controller;

import com.qf.entity.WsMsg;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ken
 * @Date 2019/2/22
 * @Version 1.0
 */
@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息给Netty服务器
     * @param wsMsg
     */
    @RequestMapping("/send")
    public void sendMsg(@RequestBody WsMsg wsMsg){
        System.out.println("需要发送的消息：" + wsMsg);
        //将该消息通过rabbitmq通知到Netty服务器集群
        rabbitTemplate.convertAndSend("msg_exchange", "", wsMsg);
    }
}
