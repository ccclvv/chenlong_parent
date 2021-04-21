package com.chenlong.user.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("携带用户登录以后令牌的实体类")
public class UserRespVo {

    @ApiModelProperty(value = "用户登录以后的令牌")
    private String accessToken;

    private String loginacct;

    private String userpswd;

    private String username;

    private String email;

    private String authstatus;

    private String usertype;

    private String realname;

    private String cardnum;

    private String accttype;


}
