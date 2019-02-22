package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.IFriendRequestDao;
import com.qf.dao.IFriendsDao;
import com.qf.entity.FriendRequest;
import com.qf.entity.Friends;
import com.qf.entity.User;
import com.qf.entity.WsMsg;
import com.qf.feign.ChatFeign;
import com.qf.feign.UserFeign;
import com.qf.service.IFriendRequestService;
import com.qf.service.IFriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author ken
 * @Date 2019/2/18
 * @Version 1.0
 */
@Service
public class FriendRequestServiceImpl implements IFriendRequestService {

    @Autowired
    private IFriendRequestDao friendRequestDao;

    @Autowired
    private IFriendsService friendsService;

    @Autowired
    private UserFeign userFeign;

    @Autowired
    private ChatFeign chatFeign;

    @Autowired
    private IFriendsDao friendsDao;

    /**
     * 申请好友
     * @param friendRequest
     * @return
     */
    @Override
    public int insertFriendRequest(FriendRequest friendRequest) {
        //判断当前这个人是否申请过添加好友申请
        QueryWrapper<FriendRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("fromid", friendRequest.getFromid());
        queryWrapper.eq("toid", friendRequest.getToid());
        queryWrapper.eq("status", 0);//

        Integer count = friendRequestDao.selectCount(queryWrapper);
        if(count > 0){
            return -1;//当前已经申请过了，原来的申请还没有处理
        }

        //这两个人是否已经是好友了
        if(friendsService.isFriends(friendRequest.getFromid(), friendRequest.getToid())){
            return -2;//当前已经是好友关系
        }

        //TODO 通知 friendRequest.getToId (被申请者) 有人给他发起了一个好友添加的申请

        //需要发送通知告诉被申请者有人申请添加他为好友
        int toid = friendRequest.getToid();
        WsMsg wsMsg = new WsMsg(friendRequest.getFromid(), toid, 101, null, null);
        chatFeign.sendMsg(wsMsg);

        //添加好友申请记录
        return friendRequestDao.insert(friendRequest);
    }

    /**
     * 查询好友申请列表
     * @param toid
     * @return
     */
    @Override
    public List<FriendRequest> queryFriendRequest(int toid) {
        QueryWrapper<FriendRequest> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("toid", toid);
        queryWrapper.orderByDesc("stime");

        //好友申请列表
        List<FriendRequest> friendRequests = friendRequestDao.selectList(queryWrapper);

        //查询好友信息
        for (FriendRequest friendRequest : friendRequests) {
            //查询申请的发起者的用户信息
            User user = userFeign.queryById(friendRequest.getFromid());
            //设置到申请对象中
            friendRequest.setFromUser(user);
        }

        return friendRequests;
    }

    /**
     * 处理好友申请记录
     * @param rid
     * @param status
     * @return
     */
    @Override
    public int friendRequestHandler(int rid, int status) {

        //通过id查询好友的申请信息
        FriendRequest friendRequest = friendRequestDao.selectById(rid);
        //修改状态
        friendRequest.setStatus(status);
        //保存修改
        int result = friendRequestDao.updateById(friendRequest);

        //通过了好友的申请
        if(result > 0 && status == 1){
            //如果是通过申请，则需要添加好友
            friendsService.insertFriends(friendRequest.getFromid(), friendRequest.getToid());
        }

        return 1;
    }

    /**
     * 查询好友列表
     * @param uid
     * @return
     */
    @Override
    public List<Friends> listByUserId(int uid) {

        QueryWrapper<Friends> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        queryWrapper.ne("status", 2);

        List<Friends> friends = friendsDao.selectList(queryWrapper);

        for (Friends friend : friends) {
            //根据好友的id查询好友的详细信息
            User user = userFeign.queryById(friend.getFid());
            //设置好友的详细信息
            friend.setFriend(user);
        }

        return friends;
    }
}
