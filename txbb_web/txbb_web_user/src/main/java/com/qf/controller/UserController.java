package com.qf.controller;

import com.qf.entity.ResultData;
import com.qf.entity.User;
import com.qf.service.IUserService;
import com.qf.util.Constact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author ken
 * @Date 2019/2/14
 * @Version 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping("/register")
    public ResultData register(User user){

        int regsiter = userService.regsiter(user);
        ResultData resultData = new ResultData();
        if(regsiter > 0){
            //注册成功
            resultData.setCode(Constact.SUCC_CODE);
            resultData.setMsg("succ");
        } else if(regsiter == -1){
            //用户名存在
            resultData.setCode(Constact.USERNAME_HAVE_CODE);
            resultData.setMsg("用户名已经存在！");
        } else {
            //其他的错误
            resultData.setCode(Constact.ERROR_CODE);
            resultData.setMsg("其他的错误！");
        }

        return resultData;
    }

    @RequestMapping("/login")
    public String login(){
        return null;
    }
}
