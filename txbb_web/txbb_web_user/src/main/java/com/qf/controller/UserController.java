package com.qf.controller;

import com.qf.entity.ResultData;
import com.qf.entity.User;
import com.qf.service.IUserService;
import com.qf.util.Constact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${fdfs.serverip}")
    private String fdfsip;

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
    public ResultData<User> login(String username, String password, String uuid){

        User user = userService.login(username, password, uuid);
        if(user != null){
            //登录成功
            user.setHeader(fdfsip + "/" + user.getHeader());
            user.setHeaderCrm(fdfsip + "/" + user.getHeaderCrm());
            user.setIdcard(fdfsip + "/" + user.getIdcard());
            return ResultData.createSuccResultData(user);
        } else {
            return ResultData.createErrorResultData(Constact.USERNAME_PASSWORD_ERROR_CODE, "用户名或者密码错误！");
        }
    }


    @RequestMapping("/updateheader")
    public ResultData<Boolean> updateUserHeader(String header, String headerCrm, Integer uid){
        System.out.println("---->" + uid);
        int result = userService.updateHeader(header, headerCrm, uid);
        if(result > 0){
            return ResultData.createSuccResultData(true);
        }

        return ResultData.createErrorResultData(Constact.ERROR_CODE, "图片修改失败！");
    }


    @RequestMapping("/searchbyusername")
    public ResultData<User> searchFriendsByUserName(String username){
        User user = userService.searchByUserName(username);
        user.setHeader(fdfsip + "/" + user.getHeader());
        user.setHeaderCrm(fdfsip + "/" + user.getHeaderCrm());
        return ResultData.createSuccResultData(user);
    }
}
