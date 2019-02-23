package com.qf.service.impl;

import com.qf.dao.IChatMsgDao;
import com.qf.entity.WsMsg;
import com.qf.service.IChatMsgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author ken
 * @Date 2019/2/23
 * @Version 1.0
 */
@Service
public class ChatMsgServiceImpl implements IChatMsgService {

    @Autowired
    private IChatMsgDao chatMsgDao;

    @Override
    public int insert(WsMsg wsMsg) {
        if(wsMsg.getId() == 0){
            //新增
            wsMsg.setStime(new Date());
            wsMsg.setCid(null);
            return chatMsgDao.insert(wsMsg);
        } else {
            //修改
            return chatMsgDao.updateById(wsMsg);
        }
    }

    @Override
    public int update(int id, int status) {
        WsMsg wsMsg = chatMsgDao.selectById(id);
        if(wsMsg != null){
            wsMsg.setStatus(status);
            return chatMsgDao.updateById(wsMsg);
        }
        return 0;
    }
}
