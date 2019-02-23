package com.qf.listener;

import com.qf.entity.WsMsg;
import com.qf.service.IChatMsgService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author ken
 * @Date 2019/2/23
 * @Version 1.0
 */
@Component
@RabbitListener(queues = "chat_queue")
public class RabbitMqListener {

    @Autowired
    private IChatMsgService chatMsgService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitHandler
    public void handelChat(WsMsg wsMsg){
        System.out.println("收到聊天信息，保存进数据库中：" + wsMsg);
        boolean isInsert = wsMsg.getId() == 0;
        int result = chatMsgService.insert(wsMsg);
        if(result > 0 && isInsert){
            //将该消息广播给WebSocket的集群，让他们发送给相应的客户端
            rabbitTemplate.convertAndSend("msg_exchange", "", wsMsg);
        }
    }
}
