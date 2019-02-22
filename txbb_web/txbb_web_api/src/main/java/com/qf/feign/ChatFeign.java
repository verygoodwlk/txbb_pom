package com.qf.feign;

import com.qf.entity.WsMsg;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author ken
 * @Date 2019/2/22
 * @Version 1.0
 */
@FeignClient("WEB-CHAT")
public interface ChatFeign {

    @RequestMapping("/chat/send")
    void sendMsg(@RequestBody WsMsg wsMsg);
}
