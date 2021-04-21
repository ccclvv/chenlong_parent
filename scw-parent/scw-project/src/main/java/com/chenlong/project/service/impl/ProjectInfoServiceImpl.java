package com.chenlong.project.service.impl;

import com.chenlong.project.mapper.*;
import com.chenlong.project.pojo.*;
import com.chenlong.project.service.ProjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectInfoServiceImpl implements ProjectInfoService {

    @Autowired(required = false)
    private TReturnMapper returnMapper;

    @Autowired(required = false)
    private TProjectMapper projectMapper;

    @Autowired(required = false)
    private TProjectImagesMapper imagesMapper;

    @Autowired(required = false)
    private TProjectTypeMapper typeMapper;

    @Autowired(required = false)
    private TProjectTagMapper tagMapper;

    /**
     * 获取所有的回报信息
     * @param projectId  项目id
     * @return
     */
    @Override
    public List<TReturn> getReturnList(Integer projectId) {
        TReturnExample example = new TReturnExample();
        TReturnExample.Criteria criteria = example.createCriteria();
        criteria.andProjectidEqualTo(projectId);
        return returnMapper.selectByExample(example);
    }

    /**
     * 获取回报信息
     * @param returnId  回报id
     * @return
     */
    @Override
    public TReturn getReturnById(Integer returnId) {
        return returnMapper.selectByPrimaryKey(returnId);
    }


    /**
     * 获取所有的项目
     * @return
     */
    public List<TProject> getAllProject(){
        return projectMapper.selectByExample(null);
    }

    /**
     * 根据项目id 获取项目信息
     * @param projectId
     * @return
     */
    public TProject getProjectById(Integer projectId){
        return projectMapper.selectByPrimaryKey(projectId);
    }


    /**
     * 获取项目的图片信息
     * @param projectId
     * @return
     */
    public List<TProjectImages> getImageByProgectId(Integer projectId){
        TProjectImagesExample example = new TProjectImagesExample();
        TProjectImagesExample.Criteria criteria = example.createCriteria();
        criteria.andProjectidEqualTo(projectId);
        return imagesMapper.selectByExample(example);
    }

    /**
     * 获取项目分类信息
     * @param projectId
     * @return
     */
    public List<TProjectType> getTypeByProjectId(Integer projectId){
        TProjectTypeExample example = new TProjectTypeExample();
        TProjectTypeExample.Criteria criteria = example.createCriteria();
        criteria.andProjectidEqualTo(projectId);
        return typeMapper.selectByExample(example);
    }

    /**
     * 获取项目标签信息
     * @param projectId
     * @return
     */
    public List<TProjectTag> getTagByProjectId(Integer projectId){
        TProjectTagExample example = new TProjectTagExample();
        TProjectTagExample.Criteria criteria = example.createCriteria();
        criteria.andProjectidEqualTo(projectId);
        return tagMapper.selectByExample(example);
    }

}
