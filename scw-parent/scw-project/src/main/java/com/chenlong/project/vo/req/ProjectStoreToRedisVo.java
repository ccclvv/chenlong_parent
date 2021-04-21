package com.chenlong.project.vo.req;

import com.chenlong.project.pojo.TReturn;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ProjectStoreToRedisVo {
    //项目令牌 (添加项目的时候使用)
    private String projectToken;
    //项目名称
    private String name;
    //项目简介
    private String remark;
    //众筹目标金额
    private Long money;
    //众筹天数
    private Integer day;
    //项目状态
    private String status;
    //发起人id
    private Integer memberId;
    //项目上架日期
    private String deploydate;
    //头部图片 每个项目只有一个
    private String hearImage;
    //详情图片  每个项目可以有多个
    private List<String> detailsImages;
    //所属分类 每个项目可以有多个分类
    private List<Integer> typeIds;
    //标签
    private List<Integer> tagIds;
    //回报
    private List<TReturn> projectReturns;

}
