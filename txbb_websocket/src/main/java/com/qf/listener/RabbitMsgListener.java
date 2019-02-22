package com.qf.listener;

import com.alibaba.fastjson.JSON;
import com.qf.entity.WsMsg;
import com.qf.websocket.group.ChannelGroupUtil;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author ken
 * @Date 2019/2/22
 * @Version 1.0
 */
@Component
@RabbitListener(queues = "msg_queue_${ws.ip}:${ws.port}")
public class RabbitMsgListener {

    @Autowired
    private ChannelGroupUtil channelGroupUtil;

    @RabbitHandler
    public void rabbitHandler(WsMsg wsMsg){
        System.out.println("WeSocet服务器从Rabbitmq上获得消息：" + wsMsg);

        //获得设备号
        String cid = wsMsg.getCid();
        //通过设备号找到Channel对象
        Channel channel = channelGroupUtil.get(cid);
        System.out.println("获得设备对应的Channel对象：" + channel);
        if(channel != null){
            //通过channel对象，发送指定的消息
            channel.writeAndFlush(new TextWebSocketFrame(JSON.toJSONString(wsMsg)));
        }
    }
}
