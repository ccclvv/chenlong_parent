package com.chenlong.project.service;

import com.chenlong.project.vo.req.ProjectStoreToRedisVo;

public interface ProjectCreateService {

    /**
     *  将项目保存到数据库
     * @param redisVo
     */
    public void saveProjectInfo(ProjectStoreToRedisVo redisVo);


}
