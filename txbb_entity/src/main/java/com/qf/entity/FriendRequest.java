package com.qf.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.util.Date;
import java.io.Serializable;

/**
 * @Author ken
 * @Date 2019/2/18
 * @Version 1.0
 */
@Data
public class FriendRequest implements Serializable {

    private int id;
    private int fromid;//申请者 - 小红
    private int toid;//被申请者 - 小明
    private Date stime;
    private String content;
    private int status;

    @TableField(exist = false)
    private User fromUser;
}
