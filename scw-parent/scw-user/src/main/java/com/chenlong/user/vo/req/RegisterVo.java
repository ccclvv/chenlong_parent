package com.chenlong.user.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RegisterVo {

    @ApiModelProperty(value = "登录的手机号")
    private String loginacct;
    @ApiModelProperty(value = "密码")
    private String userpswd;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "邮箱")
    private String email;
    @ApiModelProperty(value = "验证码")
    private String code;

}
