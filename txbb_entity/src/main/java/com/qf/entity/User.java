package com.qf.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author ken
 * @Date 2019/2/14
 * @Version 1.0
 */
@Data
public class User implements Serializable {

    private int id;
    private String username;
    private String password;
    private String nickname;
    private String header;
    private String headerCrm;
    private String pinyin;
    private int status;
    private String idcard;//二维码
}
