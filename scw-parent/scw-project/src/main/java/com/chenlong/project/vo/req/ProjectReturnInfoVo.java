package com.chenlong.project.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("项目回报信息")
@Data
public class ProjectReturnInfoVo {

    @ApiModelProperty("项目令牌")
    private String projectToken;
    @ApiModelProperty("用户令牌")
    private String accessToken;
    @ApiModelProperty("0 - 实物回报， 1 虚拟物品回报")
    private String type;
    @ApiModelProperty("回报金额")
    private Integer supportmoney;
    @ApiModelProperty("回报内容")
    private String content;
    @ApiModelProperty("回报数量")
    private Integer count;
    @ApiModelProperty("单笔限购")
    private Integer signalpurchase;
    @ApiModelProperty("限购数量")
    private Integer purchase;
    @ApiModelProperty("运费")
    private Integer freight;
    @ApiModelProperty("0 - 不开发票， 1 - 开发票")
    private String invoice;
    @ApiModelProperty("回报时间")
    private Integer rtndate;

}
