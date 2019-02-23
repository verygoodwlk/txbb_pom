package com.qf.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author ken
 * @Date 2019/2/22
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("chat_msg")
public class WsMsg implements Serializable {

    private int fromid;
    private int toid;

    /**
     * 1 - 连接请求
     * 2 - 心跳消息
     * 3 - 单聊消息
     * 4 - 群聊消息
     *
     *
     * 100 - 强制设备下线
     * 101 - 有人申请添加好友
     */
    @TableField(exist = false)
    private int type;
    @TableField(exist = false)
    private String cid;

    @TableField("msg_content")
    private String content;

    //单聊的字段
    /**
     * 内容的类型
     * 1 - 文本
     * 2 - 图片
     * 3 - 音频
     */
    private int mtype;
    @TableId(type = IdType.AUTO)
    private int id;
    private Date stime;
    private int status = 0;//默认未读消息
}
