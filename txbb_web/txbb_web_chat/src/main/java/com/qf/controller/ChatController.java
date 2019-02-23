package com.qf.controller;

import com.qf.entity.ResultData;
import com.qf.entity.WsMsg;
import com.qf.service.IChatMsgService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private IChatMsgService chatMsgService;

    /**
     * 发送消息给Netty服务器
     * @param wsMsg
     */
    @RequestMapping("/send")
    public void sendMsg(@RequestBody WsMsg wsMsg){
        System.out.println("需要发送的消息：" + wsMsg);
        //将该消息通过rabbitmq通知到Netty服务器集群
        if(wsMsg.getCid() == null){
            //找到设备id
            String cid  = (String) redisTemplate.opsForValue().get(wsMsg.getToid());
            wsMsg.setCid(cid);
        }

        rabbitTemplate.convertAndSend("msg_exchange", "", wsMsg);
    }

    /**
     * 将指定用户的所有未读消息更新为已读消息
     */
    @RequestMapping("/changestatus")
    public ResultData<Boolean> updateChatState(int uid, int fid){
        chatMsgService.updateChatStatus2Read(uid, fid);
        return ResultData.createSuccResultData(true);
    }

    /**
     * 将指定用户所有的未读消息发送给该用户
     * @return
     */
    @RequestMapping("/getNoReadInfo")
    public ResultData<Boolean> sendAllNoReadInfo(int uid){
        //所有的未读消息
        List<WsMsg> wsMsgs = chatMsgService.queryAllNoRead(uid);

        //将所有未读消息放入消息队列，让Netty服务器发送给客户端
        for (WsMsg wsMsg : wsMsgs) {
            wsMsg.setType(3);
            rabbitTemplate.convertAndSend("msg_exchange", "", wsMsg);
        }

        return ResultData.createSuccResultData(true);
    }
}
