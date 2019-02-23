package com.qf.service;

import com.qf.entity.WsMsg;

/**
 * @Author ken
 * @Date 2019/2/23
 * @Version 1.0
 */
public interface IChatMsgService {

    int insert(WsMsg wsMsg);

    int update(int id, int status);
}
