package com.chenlong.project.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.chenlong.project.enums.ProjectImageTypeEnume;
import com.chenlong.project.mapper.*;
import com.chenlong.project.pojo.*;
import com.chenlong.project.service.ProjectCreateService;
import com.chenlong.project.vo.req.ProjectStoreToRedisVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProjectCreateServiceImpl implements ProjectCreateService {

    @Autowired(required = false)
    private TProjectMapper projectMapper;

    @Autowired(required = false)
    private TProjectImagesMapper imagesMapper;

    @Autowired(required = false)
    private TProjectTypeMapper typeMapper;

    @Autowired(required = false)
    private TProjectTagMapper tagMapper;

    @Autowired(required = false)
    private TReturnMapper returnMapper;

    /**
     *  保存项目到数据库中
     * @param redisVo
     */
    @Override
    public void saveProjectInfo(ProjectStoreToRedisVo redisVo) {
        System.out.println(redisVo.toString());
        //先插入项目表
        TProject project = new TProject();
        BeanUtils.copyProperties(redisVo,project);
        //创建时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        project.setCreatedate(dateFormat.format(new Date()));
        projectMapper.insert(project);
        //获取刚插入的项目id
        Integer id = project.getId();

        //插入image表
        //添加头图片信息
        TProjectImages images = new TProjectImages();
        images.setProjectid(id);
        images.setImgurl(redisVo.getHearImage());
        images.setImgtype(ProjectImageTypeEnume.HEADER.getCode()); //(byte)0
        imagesMapper.insert(images);
        //添加详情图
        List<String> detailsImages = redisVo.getDetailsImages();
        if (detailsImages != null && detailsImages.size() != 0){
            for (String imgUrl : detailsImages){
                images.setProjectid(id);
                images.setImgurl(redisVo.getHearImage());
                images.setImgtype(ProjectImageTypeEnume.DETAILS.getCode()); //(byte)1
                imagesMapper.insert(images);
            }
        }

        //插入标签表
        TProjectType type = new TProjectType();
        List<Integer> typeIds = redisVo.getTypeIds();
        if ( typeIds != null && typeIds.size() != 0){
            for (Integer typeId : typeIds){
                type.setProjectid(id);
                type.setTypeid(typeId);
                typeMapper.insert(type);
            }
        }

        //插入tag表
        TProjectTag tag = new TProjectTag();
        List<Integer> tagIds = redisVo.getTagIds();
        if ( tagIds != null && tagIds.size() != 0){
            for (Integer tagId : tagIds){
                tag.setProjectid(id);
                tag.setTagid(tagId);
                tagMapper.insert(tag);
            }
        }

        //插入回报信息
        List<TReturn> returns = redisVo.getProjectReturns();
        if (returns != null && returns.size() != 0){
            for (TReturn aReturn : returns) {
                aReturn.setProjectid(id);
                returnMapper.insert(aReturn);
            }
        }
    }



}
