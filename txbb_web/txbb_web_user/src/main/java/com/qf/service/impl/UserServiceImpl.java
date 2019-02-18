package com.qf.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.dao.IUserDao;
import com.qf.entity.User;
import com.qf.service.IUserService;
import com.qf.util.MD5Util;
import com.qf.util.PinyinUtils;
import com.qf.util.QRCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @Author ken
 * @Date 2019/2/14
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Autowired
    private FastFileStorageClient fastFileStorageClient;


    @Override
    public int regsiter(User user) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        User u = userDao.selectOne(queryWrapper);
        if(u != null){
            //用户名存在
            return -1;
        }

        //对密码进行md5加密
        user.setPassword(MD5Util.md5(user.getPassword()));
        //生成二维码 - fastdfs

        //生成一个临时文件保存二维码
        File file = null;
        try {
            file = File.createTempFile(user.getUsername()+"qrcode", ".png");

            //生成二维码
            boolean flag = QRCodeUtils.createQRCode(file, "txbb:" + user.getUsername());
            if(flag){
                //将二维码上传到fastdfs
                StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(
                        new FileInputStream(file),
                        file.length(),
                        "PNG",
                        null);

                user.setIdcard(storePath.getFullPath());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(file != null){
                file.delete();
            }
        }

        //生成昵称的拼音
        String pinyin = PinyinUtils.str2Pinyin(user.getNickname());
        user.setPinyin(pinyin.toUpperCase());

        return userDao.insert(user);
    }

    @Override
    public User login(String username, String password, String uuid) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("username", username);
        queryWrapper.eq("password", MD5Util.md5(password));
        User user = userDao.selectOne(queryWrapper);

        if(user != null){
            //TODO 将登录用户和设备进行绑定
        }

        return user;
    }

    @Override
    public int updateHeader(String header, String headerCrm, Integer uid) {
        User user = userDao.selectById(uid);
        if(user != null) {
            user.setHeader(header);
            user.setHeaderCrm(headerCrm);
            return userDao.updateById(user);
        }
        return 0;
    }

    @Override
    public User searchByUserName(String username) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);
        return userDao.selectOne(queryWrapper);
    }

    @Override
    public User queryById(int id) {
        return userDao.selectById(id);
    }
}
