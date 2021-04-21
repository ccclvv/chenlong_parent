package com.chenlong.project.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel("项目基本信息")
@Data
public class ProjectBaseInfoVo {

    @ApiModelProperty("项目令牌")
    private String projectToken;
    @ApiModelProperty("用户令牌")
    private String accessToken;   //验证用户是否登录
    @ApiModelProperty("项目名称")
    private String name;
    @ApiModelProperty("项目简介")
    private String remark;
    @ApiModelProperty("项目上架日期")
    private String deploydate;
    @ApiModelProperty("众筹目标金额")
    private Long money;
    @ApiModelProperty("众筹天数")
    private Integer day;
    @ApiModelProperty("头部图片 每个项目只有一个")
    private String hearImage;
    @ApiModelProperty("详情图片  每个项目可以有多个")
    private List<String> detailsImages;
    @ApiModelProperty("所属分类 每个项目可以有多个分类")
    private List<Integer> typeIds;
    @ApiModelProperty("项目标签(规格)")
    private List<Integer> tagIds;

}
