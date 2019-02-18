package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qf.dao.IFriendsDao;
import com.qf.entity.Friends;
import com.qf.service.IFriendsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @Author ken
 * @Date 2019/2/18
 * @Version 1.0
 */
@Service
public class IFriendsServiceImpl implements IFriendsService {

    @Autowired
    private IFriendsDao friendsDao;
    
    /**
     * 判断两个人是否为好友
     * @param uid
     * @param fid
     * @return
     */
    @Override
    public boolean isFriends(int uid, int fid) {

        QueryWrapper<Friends> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("uid", uid);
        queryWrapper.eq("fid", fid);
        queryWrapper.ne("status", 2);

        Integer count = friendsDao.selectCount(queryWrapper);

        return count > 0 ? true : false;
    }

    /**
     * 添加好友
     * @param uid
     * @param fid
     * @return
     */
    @Override
    public int insertFriends(int uid, int fid) {

        Friends friends = new Friends();
        friends.setUid(uid);
        friends.setFid(fid);
        friends.setCtime(new Date());
        friends.setStatus(0);

        if(!isFriends(uid, fid)){
            friendsDao.insert(friends);
        }

        if(!isFriends(fid, uid)){
            friends.setFid(uid);
            friends.setUid(fid);
            friendsDao.insert(friends);
        }

        return 1;
    }
}
