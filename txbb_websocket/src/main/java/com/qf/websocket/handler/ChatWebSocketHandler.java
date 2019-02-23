package com.qf.websocket.handler;

import com.qf.entity.WsMsg;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * 处理单聊消息
 *
 * @Author ken
 * @Date 2019/2/23
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class ChatWebSocketHandler extends SimpleChannelInboundHandler<WsMsg> {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WsMsg wsMsg) throws Exception {
        if(wsMsg.getType() == 3){
            //当前是一个单聊消息
            rabbitTemplate.convertAndSend("chat_queue", wsMsg);

        } else {
            ctx.fireChannelRead(wsMsg);
        }
    }
}
