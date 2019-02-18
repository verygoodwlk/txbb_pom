package com.qf.service;

/**
 * @Author ken
 * @Time 2019/2/18 9:35
 * @Version 1.0
 */
public interface IFriendsService {

    boolean isFriends(int uid, int fid);

    int insertFriends(int uid, int fid);
}
