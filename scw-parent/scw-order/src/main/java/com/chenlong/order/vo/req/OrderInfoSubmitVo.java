package com.chenlong.order.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("要提交的订单数据")
public class OrderInfoSubmitVo {
    @ApiModelProperty("用户令牌")
    private String accessToken;
    @ApiModelProperty("项目id")
    private Integer projectid;
    @ApiModelProperty("回报id")
    private Integer returnid;
    //订单编号
    //private String ordernum;

    //private String createdate;

    //private Integer money;  //订单支付价格  通过计算获得

    @ApiModelProperty("回报数量")
    private Integer rtncount;

    //private String status;
    @ApiModelProperty("收件地址")
    private String address;
    @ApiModelProperty("是否开发票, 0 - 不开发票， 1 - 开发票")
    private String invoice;
    @ApiModelProperty("发票的title")
    private String invoictitle;
    @ApiModelProperty("备注")
    private String remark;

}
