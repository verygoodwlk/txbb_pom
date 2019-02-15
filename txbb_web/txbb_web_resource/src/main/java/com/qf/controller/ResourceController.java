package com.qf.controller;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.qf.entity.ResultData;
import com.qf.feign.UserFeign;
import com.qf.util.Constact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author ken
 * @Date 2019/2/15
 * @Version 1.0
 */
@RestController
@RequestMapping("/res")
public class ResourceController {

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

    @Autowired
    private UserFeign userFeign;

    @Value("${fdfs.serverip}")
    private String serverip;

    @RequestMapping("/img")
    public ResultData<Map> uploadImg(MultipartFile file, Integer uid){
        System.out.println("---->" + uid);

        try {
            StorePath result = fastFileStorageClient.uploadImageAndCrtThumbImage(
                    file.getInputStream(),
                    file.getSize(),
                    "PNG",
                    null);

            //获得上传的路径
            String fullpath = result.getFullPath();
            //缩略图的路径
            String crmpath = fullpath.replace(".", "_80x80.");

            //将两个地址封装到Map集合中
            Map<String, String> map = new HashMap<>();
            map.put("header", "http://" + serverip + "/" + fullpath);
            map.put("headerCrm", "http://" + serverip + "/" + crmpath);

            //将上传的图片保存到数据库中
            ResultData<Boolean> booleanResultData = userFeign.updateUserHeader(fullpath, crmpath, uid);

            if(booleanResultData.getCode().equals("0000")){
                //返回成功的数据
                return ResultData.createSuccResultData(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //返回错误结果
        return ResultData.createErrorResultData(Constact.UPLOAD_IMAGE_ERROR_CODE, "图片上传失败！");
    }
}
