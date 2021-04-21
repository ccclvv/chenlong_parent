package com.chenlong.user.controller;

import com.alibaba.druid.util.StringUtils;
import com.chenlong.common.response.AppResponse;
import com.chenlong.user.component.SmsTemplate;
import com.chenlong.user.pojo.TMember;
import com.chenlong.user.pojo.TMemberAddress;
import com.chenlong.user.service.UserService;
import com.chenlong.user.vo.req.RegisterVo;
import com.chenlong.user.vo.resp.UserRespVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.xml.bind.Element;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Api(tags = "用户模块: 完成用户的注册,登录,获取验证码")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private SmsTemplate smsTemplate;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @ApiOperation("获取验证码")
    @PostMapping("/sendSms")
    public AppResponse<Object> sendSms(String mobile){
        //创建一个验证码
        Random random = new Random();
        int i = random.nextInt(900000);
        String code = i + 100000 + "";
        //保存到数据库  设置只保存5分钟
        redisTemplate.opsForValue().set(mobile, code, 5, TimeUnit.MINUTES);
        //发送信息
        String s = smsTemplate.sendSms(mobile, code);
        if (s == null || "fail".equals(s)){
            return AppResponse.fail("短信发送失败");
        }
        return AppResponse.ok("发送成功");
    }

    @ApiOperation("用户注册")
    @PostMapping("/register")
    public AppResponse<Object> register(RegisterVo registerVo){
        //验证验证码
        String code = redisTemplate.opsForValue().get(registerVo.getLoginacct());
        if (!StringUtils.isEmpty(code)){
            if (code.equals(registerVo.getCode())){
                //验证码正确
                TMember member = new TMember();
                BeanUtils.copyProperties(registerVo, member);
                try {
                    userService.register(member);
                    return AppResponse.ok("注册成功");
                } catch (Exception e) {
                    e.printStackTrace();
                    //注册未成功  未知错误
                    return AppResponse.fail(e.getMessage());
                }
            }
            return AppResponse.fail("验证码错误");
        }else {
            return AppResponse.fail("验证码已过期");
        }
    }

    @ApiOperation("用户登录")
    @PostMapping("/login")
    public AppResponse<UserRespVo> login(String loginacct, String userpswd){
        TMember member = userService.login(loginacct, userpswd);
        if (member == null){
            //登录失败
            AppResponse resp = AppResponse.fail(null);
            resp.setMsg("用户名或密码错误,请重新登录");
            return resp;
        }
        //登录成功
        //生成令牌
        String token = UUID.randomUUID().toString().replace("-", "");//将里面的-  替换为空
        // 创建响应对象  设置属性
        UserRespVo respVo = new UserRespVo();
        BeanUtils.copyProperties(member, respVo);
        respVo.setAccessToken(token);
        // 将令牌保存到redis
        //格式  键:令牌 ==> 值:用户id  保存两个小时
        redisTemplate.opsForValue().set(token, member.getId().toString(), 2, TimeUnit.HOURS);
        return AppResponse.ok(respVo);
    }

    @ApiOperation("根据id获取用户信息")
    @GetMapping("/getUserById/{id}")
    public AppResponse<UserRespVo> getUserById(@PathVariable("id")Integer id){
        TMember member = userService.getUserbyId(id);
        if (member == null){
            AppResponse resp = AppResponse.fail(null);
            resp.setMsg("用户不存在");
            return resp;
        }
        //用户存在
        UserRespVo respVo = new UserRespVo();
        BeanUtils.copyProperties(member, respVo);
        return AppResponse.ok(respVo);
    }

    @ApiOperation("获取用户收货地址")
    @ApiParam("用户令牌")
    @GetMapping("/getUserById/{accessToken}")
    public AppResponse<List<TMemberAddress>> getAddressByMemberId(String accessToken){
        String memberId = redisTemplate.opsForValue().get(accessToken);
        if (StringUtils.isEmpty(memberId)){
            AppResponse<List<TMemberAddress>> resp = AppResponse.fail(null);
            resp.setMsg("用户没有登录");
            return resp;
        }
        try {
            List<TMemberAddress> address = userService.getAddressByMemberId(Integer.parseInt(memberId));
            return AppResponse.ok(address);
        } catch (Exception e) {
            //获取失败
            e.printStackTrace();
            return AppResponse.fail(null);
        }
    }
}
