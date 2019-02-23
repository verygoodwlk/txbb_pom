package com.qf.service;

import com.qf.entity.WsMsg;

import java.util.List;

/**
 * @Author ken
 * @Date 2019/2/23
 * @Version 1.0
 */
public interface IChatMsgService {

    int insert(WsMsg wsMsg);

    int updateChatStatus2Read(int uid,int fid);

    int update(int id, int status);

    List<WsMsg> queryAllNoRead(int uid);
}
