package com.chenlong.project.service;

import com.chenlong.project.pojo.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface ProjectInfoService {

    //根据项目id获取所有的回报信息
    public List<TReturn> getReturnList(Integer projectId);

    //根据回报id获取回报信息
    public TReturn getReturnById(Integer returnId);

    //获取所有项目信息
    public List<TProject> getAllProject();

    //获取项目的图片信息
    public List<TProjectImages> getImageByProgectId(Integer projectId);

    //根据项目id 获取项目信息
    public TProject getProjectById(Integer projectId);

    //获取项目分类信息
    public List<TProjectType> getTypeByProjectId(Integer projectId);

    //获取项目标签信息
    public List<TProjectTag> getTagByProjectId(Integer projectId);

}
