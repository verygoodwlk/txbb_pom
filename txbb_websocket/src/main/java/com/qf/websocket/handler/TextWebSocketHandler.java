package com.qf.websocket.handler;

import com.alibaba.fastjson.JSON;
import com.qf.entity.WsMsg;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import org.springframework.stereotype.Component;

/**
 * 文本帧处理器 - 将接受的json转换成实体类
 * @Author ken
 * @Date 2019/2/21
 * @Version 1.0
 */
@Component
@ChannelHandler.Sharable
public class TextWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame textWebSocketFrame) throws Exception {
        //获得连接对象
        Channel channel = ctx.channel();
        //获得当前的消息
        String msg = textWebSocketFrame.text();
        //尝试将msg转换成WsMsg对象
        WsMsg wsMsg = null;
        try {
            wsMsg = JSON.parseObject(msg, WsMsg.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(wsMsg != null && wsMsg.getType() > 0){
            //格式符合协议规则
            //消息的透传，将该数据透传给下一个ChannelHandler处理
            ctx.fireChannelRead(wsMsg);
        } else {
            System.out.println("客户端数据格式异常！");
            channel.close();
        }
    }
}
