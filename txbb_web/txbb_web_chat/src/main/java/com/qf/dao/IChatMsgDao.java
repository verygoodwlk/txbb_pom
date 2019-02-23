package com.qf.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qf.entity.WsMsg;
import org.apache.ibatis.annotations.Param;

/**
 * @Author ken
 * @Time 2019/2/23 10:19
 * @Version 1.0
 */
public interface IChatMsgDao extends BaseMapper<WsMsg> {

    int updateStatus2Read(@Param("uid") int uid, @Param("fid") int fid);
}
