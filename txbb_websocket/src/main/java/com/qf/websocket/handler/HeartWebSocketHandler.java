package com.qf.websocket.handler;

import com.qf.entity.WsMsg;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.stereotype.Component;

/**
 *
 * 心跳消息Handler
 *
 * @Author ken
 * @Date 2019/2/22
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class HeartWebSocketHandler extends SimpleChannelInboundHandler<WsMsg> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WsMsg wsMsg) throws Exception {
        if(wsMsg.getType() == 2){
            //当前是一个心跳消息
        } else {
            //非心跳消息，继续往后透传
            ctx.fireChannelRead(wsMsg);
        }
    }
}
