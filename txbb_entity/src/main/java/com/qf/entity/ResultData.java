package com.qf.entity;

import com.qf.util.Constact;
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

    /**
     * 构建一个成功的返回对象
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResultData<T> createSuccResultData(T data){
        ResultData<T> resultData = new ResultData<>();
        resultData.setCode(Constact.SUCC_CODE);
        resultData.setMsg("succ");
        resultData.setData(data);
        return resultData;
    }

    /**
     * 构建一个失败的返回对象
     * @param code
     * @param msg
     * @param <T>
     * @return
     */
    public static <T> ResultData<T> createErrorResultData(String code, String msg){
        ResultData<T> resultData = new ResultData<>();
        resultData.setCode(code);
        resultData.setMsg(msg);
        resultData.setData(null);
        return resultData;
    }

}
