package com.qf.feign;

import com.qf.entity.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author ken
 * @Date 2019/2/15
 * @Version 1.0
 */
@FeignClient(value = "WEB-USER")
public interface UserFeign {

    @RequestMapping("/user/updateheader")
    ResultData<Boolean> updateUserHeader(
            @RequestParam("header") String header,
            @RequestParam("headerCrm") String headerCrm,
            @RequestParam("uid") Integer uid);
}
