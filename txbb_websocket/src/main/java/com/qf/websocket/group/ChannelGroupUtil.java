package com.qf.websocket.group;

import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * @Author ken
 * @Date 2019/2/22
 * @Version 1.0
 */
@Component
public class ChannelGroupUtil {

    /**
     * 线程安全的map
     */
    private Map<String, Channel> channelMap = new ConcurrentHashMap<>();

    /**
     * 管理设备号和Channel对象
     * @param cid
     * @param channel
     */
    public Channel put(String cid, Channel channel){
        return channelMap.put(cid, channel);
    }

    /**
     * 判断当前管理的链接长度
     * @return
     */
    public int size(){
       return channelMap.size();
    }


    /**
     * 根据设备号查询Channel对象
     * @param cid
     * @return
     */
    public Channel get(String cid){
        return channelMap.get(cid);
    }

    /**
     * 根据设备号移除Channel
     * @return
     */
    public Channel removeByCid(String cid){
        return channelMap.remove(cid);
    }

    /**
     * 根据Channel对象，移除集合数据
     * @param channel
     * @return
     */
    public boolean removeByChannel(Channel channel){
        Set<Map.Entry<String, Channel>> entries = channelMap.entrySet();

        for (Map.Entry<String, Channel> entry : new HashSet<>(entries)) {
            if(entry.getValue() == channel){
                return entries.remove(entry.getKey());
            }
        }

        return false;
    }
}
