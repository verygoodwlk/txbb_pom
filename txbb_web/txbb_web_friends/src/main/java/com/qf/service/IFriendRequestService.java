package com.qf.service;

import com.qf.entity.FriendRequest;
import com.qf.entity.Friends;

import java.util.List;

/**
 * @Author ken
 * @Time 2019/2/18 9:27
 * @Version 1.0
 */
public interface IFriendRequestService {

    int insertFriendRequest(FriendRequest friendRequest);

    List<FriendRequest> queryFriendRequest(int toid);

    int friendRequestHandler(int rid, int status);

    List<Friends> listByUserId(int uid);
}
