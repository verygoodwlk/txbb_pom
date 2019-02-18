package com.qf.service;

import com.qf.entity.User;

/**
 * @Author ken
 * @Time 2019/2/14 15:37
 * @Version 1.0
 */
public interface IUserService {

    int regsiter(User user);

    User login(String username, String password, String uuid);

    int updateHeader(String header, String headerCrm, Integer uid);

    User searchByUserName(String username);

    User queryById(int id);
}
