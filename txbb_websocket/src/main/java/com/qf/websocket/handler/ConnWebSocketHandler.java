package com.qf.websocket.handler;

import com.qf.entity.WsMsg;
import com.qf.websocket.group.ChannelGroupUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * 处理连接握手的Handler
 *
 * @Author ken
 * @Date 2019/2/22
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class ConnWebSocketHandler extends SimpleChannelInboundHandler<WsMsg> {

    @Autowired
    private ChannelGroupUtil channelGroupUtil;

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有一个连接上线！");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("有一个连接下线");
        channelGroupUtil.removeByChannel(ctx.channel());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WsMsg wsMsg) throws Exception {
        if(wsMsg.getType() == 1){
            //握手请求

            //获得当前的Channel对象，并且和设备对象进行统一管理
            Channel channel = ctx.channel();
            
            //获得连接的设备号
            String cid = wsMsg.getCid();

            //放入集合中统一管理
            channelGroupUtil.put(cid, channel);

            System.out.println("有一个连接握手成功！" + wsMsg + " 当前管理的链接数量：" + channelGroupUtil.size());

        } else {
            //非握手请求，继续往后透传
            ctx.fireChannelRead(wsMsg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        channelGroupUtil.removeByChannel(ctx.channel());
    }
}
