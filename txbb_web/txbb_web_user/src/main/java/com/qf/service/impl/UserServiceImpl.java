package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.IUserDao;
import com.qf.entity.User;
import com.qf.service.IUserService;
import com.qf.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author ken
 * @Date 2019/2/14
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    public int regsiter(User user) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", user.getUsername());
        User u = userDao.selectOne(queryWrapper);
        if(u != null){
            //用户名存在
            return -1;
        }

        //对密码进行md5加密
        user.setPassword(MD5Util.md5(user.getPassword()));

        return userDao.insert(user);
    }

    @Override
    public User login(String username, String password) {
        return null;
    }
}
