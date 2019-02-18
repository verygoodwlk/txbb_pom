package com.qf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;
import java.util.*;

/**
 * @Author ken
 * @Date 2019/2/18
 * @Version 1.0
 */
@Data
public class Friends implements Serializable {

    private int uid;
    private int fid;
    private Date ctime;
    private int status;//0 - 普通好友 1 - 黑名单 2 - 已删除

    @TableField(exist = false)
    private User friend;
}
