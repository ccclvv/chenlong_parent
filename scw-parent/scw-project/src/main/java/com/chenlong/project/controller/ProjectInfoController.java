package com.chenlong.project.controller;

import com.chenlong.common.response.AppResponse;
import com.chenlong.project.pojo.*;
import com.chenlong.project.service.ProjectInfoService;
import com.chenlong.project.vo.resp.ProjectDetailVo;
import com.chenlong.project.vo.resp.ProjectVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.BeanDefinitionDsl;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.beans.Beans;
import java.util.ArrayList;
import java.util.List;

@Api(tags = "获取项目信息")
@RestController
@RequestMapping("/projectInfo")
public class ProjectInfoController {

    @Autowired
    private ProjectInfoService infoService;

    @ApiOperation("根据项目id获取回报信息")
    @GetMapping("/getReturns/{projectId}")
    public AppResponse<List<TReturn>> getReturnList(@PathVariable("projectId") Integer projectId){
        try {
            List<TReturn> returns = infoService.getReturnList(projectId);
            return AppResponse.ok(returns);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.fail(null);
        }
    }

    @ApiOperation("根据回报id获取回报信息")
    @GetMapping("/getReturn/{returnId}")
    public AppResponse<TReturn> getReturnById(@PathVariable("returnId") Integer returnId){
        try {
            TReturn tReturn = infoService.getReturnById(returnId);
            return AppResponse.ok(tReturn);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.fail(null);
        }
    }



    @ApiOperation("获取所有项目信息")
    @GetMapping("/getAllProject")
    public AppResponse<List<ProjectVo>> getAllProject(){
        List<TProject> allProject = infoService.getAllProject();
        if (allProject == null && allProject.size() == 0){
            AppResponse<List<ProjectVo>> resp = AppResponse.fail(null);
            resp.setMsg("获取所有项目信息失败");
            return resp;
        }
        //准备要返回的数据
        List<ProjectVo> pvos = new ArrayList<>();
        ProjectVo pvo = new ProjectVo();
        for (TProject pro : allProject) {
            //先拷贝数据
            BeanUtils.copyProperties(pro, pvo);
            List<TProjectImages> images = infoService.getImageByProgectId(pro.getId());
            if (images == null && images.size() == 0){
                //没有项目图片
                pvo.setHeardImage(null);
            }else {
                //有图片
                for (TProjectImages image : images) {
                    if (image.getImgtype() == 0){
                        pvo.setHeardImage(image.getImgurl());
                    }
                }
            }
            //处理好数据后  加入集合中
            pvos.add(pvo);
        }
        return AppResponse.ok(pvos);
    }

    @ApiOperation("根据项目id 获取项目的详情")
    @GetMapping("/getProjectByProjectId/{projectId}")
    public AppResponse<ProjectDetailVo> getProjectByProjectId(@PathVariable("projectId") Integer projectId){
        TProject pro = infoService.getProjectById(projectId);
        if (pro == null){
            AppResponse<ProjectDetailVo> resp = AppResponse.fail(null);
            resp.setMsg("获取所有项目信息失败");
            return resp;
        }
        //准备要返回的数据
        ProjectDetailVo pvo = new ProjectDetailVo();

        //添加图片信息
        //先拷贝数据
        BeanUtils.copyProperties(pro, pvo);
        List<TProjectImages> images = infoService.getImageByProgectId(projectId);
        if (images == null && images.size() == 0){
            //没有项目图片
            pvo.setImages(null);
        }else {
            //有图片
            //先添加头图片
            for (TProjectImages image : images) {
                if (image.getImgtype() == 0){
                    pvo.setHeaderImage(image.getImgurl());
                }
            }
            pvo.setImages(images);
        }

        //添加回报信息
        List<TReturn> returnList = infoService.getReturnList(projectId);
        if (returnList == null && returnList.size() == 0){
            //没有回报信息
            pvo.setReturns(null);
        }else {
            //有回报信息
            pvo.setReturns(returnList);
        }

        //添加分类信息
        pvo.setTypes(infoService.getTypeByProjectId(projectId));
        //添加标签信息
        pvo.setTags(infoService.getTagByProjectId(projectId));

        return AppResponse.ok(pvo);
    }


    @ApiOperation("获取项目图片信息")
    @GetMapping("/getImageByProgectId/{projectId}")
    public AppResponse<List<TProjectImages>> getImageByProgectId(@PathVariable("projectId") Integer projectId){
        try {
            List<TProjectImages> images = infoService.getImageByProgectId(projectId);
            return AppResponse.ok(images);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.fail(null);
        }
    }

    @ApiOperation("获取项目分类信息")
    @GetMapping("/getTypeByProjectId/{projectId}")
    public AppResponse<List<TProjectType>> getTypeByProjectId(@PathVariable("projectId") Integer projectId){
        try {
            List<TProjectType> types = infoService.getTypeByProjectId(projectId);
            return AppResponse.ok(types);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.fail(null);
        }
    }

    @ApiOperation("获取项目标签信息")
    @GetMapping("/getTagByProjectId/{projectId}")
    public AppResponse<List<TProjectTag>> getTagByProjectId(@PathVariable("projectId") Integer projectId){
        try {
            List<TProjectTag> tags = infoService.getTagByProjectId(projectId);
            return AppResponse.ok(tags);
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.fail(null);
        }
    }

}
