package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author ken
 * @Date 2019/2/22
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WsMsg<T> implements Serializable {

    private int fromid;
    private int toid;

    /**
     * 1 - 连接请求
     * 2 - 心跳消息
     *
     *
     * 100 - 强制设备下线
     */
    private int type;
    private String cid;
    private T content;
}
