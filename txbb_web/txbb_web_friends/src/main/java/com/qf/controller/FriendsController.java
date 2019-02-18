package com.qf.controller;

import com.qf.entity.FriendRequest;
import com.qf.entity.ResultData;
import com.qf.service.IFriendRequestService;
import com.qf.util.Constact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author ken
 * @Date 2019/2/18
 * @Version 1.0
 */
@RestController
@RequestMapping("/friend")
public class FriendsController {


    @Autowired
    private IFriendRequestService friendRequestService;
    
    /**
     * 添加好友申请信息
     * @return
     */
    @RequestMapping("/insertFriendRequest")
    public ResultData<Boolean> insertFriendRequest(FriendRequest friendRequest){
        int result = friendRequestService.insertFriendRequest(friendRequest);
        if(result > 0){
            //添加成功
            return ResultData.createSuccResultData(true);
        } else if(result == -1){
            //已经申请过
            return ResultData.createErrorResultData(Constact.ERROR_CODE, "请不要重复申请！");
        } else if(result == -2){
            //已经是好友
            return ResultData.createErrorResultData(Constact.ERROR_CODE, "已经是好友关系！");
        }
        return ResultData.createErrorResultData(Constact.ERROR_CODE, "服务器异常！");
    }

    /**
     * 根据用户信息，查询发送给这个用户的所有好友申请
     * @param toid
     * @return
     */
    @RequestMapping("/queryFriendRequest")
    public ResultData<List<FriendRequest>> queryFriendRequest(int toid){
        List<FriendRequest> friendRequests = friendRequestService.queryFriendRequest(toid);
        if(friendRequests != null && friendRequests.size() > 0){
            return ResultData.createSuccResultData(friendRequests);
        }
        return ResultData.createErrorResultData(Constact.ERROR_CODE, "没有任何添加申请");
    }

    /**
     * 好友申请记录的处理
     * @return
     */
    @RequestMapping("/friendRequestHandler")
    public ResultData<Boolean> friendRequestHandler(int rid, int status){
        int result = friendRequestService.friendRequestHandler(rid, status);
        return ResultData.createSuccResultData(true);
    }
}
