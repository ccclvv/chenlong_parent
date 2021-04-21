package com.chenlong.project.controller;

import com.chenlong.common.response.AppResponse;
import com.chenlong.project.utils.OssTemplate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/project")
@Api(tags = "项目模块, 用来完成文件上传 ")
public class ProjectController {

    @Autowired
    private OssTemplate ossTemplate;

    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public AppResponse<Map<String, Object>> upload(@RequestParam("file") MultipartFile[] files){
        Map<String, Object> map = new HashMap();
        List<String> list = new ArrayList();
        if (files != null && files.length > 0){
            // 前台可能上传的不止一个文件  所以用数组接收 遍历上传
            for (MultipartFile file : files) {
                try {
                    //使用工具类上传  返回的是文件访问路径
                    String url = ossTemplate.upload(file.getInputStream(), file.getOriginalFilename());
                    list.add(url);
                } catch (IOException e) {
                    e.printStackTrace();
                    AppResponse<Map<String, Object>> resp = AppResponse.fail(map);
                    resp.setMsg("文件上传失败,请检查网络环境");
                    return resp;
                }
            }
            map.put("urls", list);
            return AppResponse.ok(map);
        }
        AppResponse<Map<String, Object>> resp = AppResponse.fail(map);
        resp.setMsg("您还没有选择文件");
        return resp;
    }

}
