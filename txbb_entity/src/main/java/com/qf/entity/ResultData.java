package com.qf.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author ken
 * @Date 2019/2/14
 * @Version 1.0
 */
@Data
public class ResultData<T> implements Serializable {
    private String code;//状态码
    private String msg;//简单的结果描述
    private T data;
}
