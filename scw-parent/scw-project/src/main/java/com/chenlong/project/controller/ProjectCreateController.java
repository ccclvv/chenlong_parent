package com.chenlong.project.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.chenlong.common.response.AppResponse;
import com.chenlong.common.vo.BaseVo;
import com.chenlong.project.pojo.TReturn;
import com.chenlong.project.service.ProjectCreateService;
import com.chenlong.project.vo.req.ProjectBaseInfoVo;
import com.chenlong.project.vo.req.ProjectReturnInfoVo;
import com.chenlong.project.vo.req.ProjectStoreToRedisVo;
import io.swagger.annotations.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/create")
@Api(tags = "创建项目的模块, 4步完成项目添加")
public class ProjectCreateController {

    @Autowired
    private ProjectCreateService projectCreateService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @ApiOperation("第一步: 初始化项目")
    @ApiParam("accessToken: 用户令牌")
    @PostMapping("/init")
    public AppResponse<String> init(BaseVo baseVo){
        //获取用户令牌  从redis中获取用户id
        String accessToken = baseVo.getAccessToken();
        String memberIdStr = redisTemplate.opsForValue().get(accessToken);
        if (StringUtils.isEmpty(memberIdStr)){
            //用户id为空
            return AppResponse.ok("用户没有登录, 请登录后重试");
        }
        //用户已经登录
        Integer memberId = Integer.parseInt(memberIdStr);
        //生成项目令牌
        String proToken = "Project_" + UUID.randomUUID().toString().replace("-", "");
        //创建初始化对象
        ProjectStoreToRedisVo redisVo = new ProjectStoreToRedisVo();
        redisVo.setProjectToken(proToken);
        redisVo.setMemberId(memberId);
        try {
            //保存到redis
            String redisStr = JSON.toJSONString(redisVo);
            redisTemplate.opsForValue().set(proToken, redisStr);
            //返回项目令牌
            return AppResponse.ok(proToken);
        } catch (Exception e) {
            e.printStackTrace();
            //失败
            return AppResponse.ok("未知错误!!!项目初始化失败");
        }
    }

    @ApiOperation("第二步: 添加项目基本信息")
    @PostMapping("/addBaseInfo")
    public AppResponse<String> addBaseInfo(ProjectBaseInfoVo baseInfoVo){
        //验证用户是否登录
        //获取用户令牌  从redis中获取用户id
        String accessToken = baseInfoVo.getAccessToken();
        String memberIdStr = redisTemplate.opsForValue().get(accessToken);
        if (StringUtils.isEmpty(memberIdStr)){
            //用户id为空
            return AppResponse.ok("用户没有登录, 请登录后重试");
        }
        //用户登录了  先取出redis中的初始化项目信息
        String projectToken = baseInfoVo.getProjectToken();
        String initStr = redisTemplate.opsForValue().get(projectToken);
        // 将用户填写的信息导入到要存到redis中的对象中--->ProjectStoreToRedisVo
        ProjectStoreToRedisVo redisVo = JSON.parseObject(initStr, ProjectStoreToRedisVo.class);
        BeanUtils.copyProperties(baseInfoVo, redisVo);
        //保存到redis
        String proStr = JSON.toJSONString(redisVo);
        redisTemplate.opsForValue().set(projectToken, proStr);
        //返回项目令牌
        return AppResponse.ok(projectToken);
    }

    @ApiOperation("第三步: 添加项目回报信息")
    @PostMapping("/addReturnInfo")
    public AppResponse<String> addReturnInfo(@RequestBody List<ProjectReturnInfoVo> returns) {
        //验证用户是否登录
        //获取用户令牌  从redis中获取用户id
        String accessToken = returns.get(0).getAccessToken();
        String memberIdStr = redisTemplate.opsForValue().get(accessToken);
        if (StringUtils.isEmpty(memberIdStr)){
            //用户id为空
            return AppResponse.ok("用户没有登录, 请登录后重试");
        }
        //用户登录了  先取出redis中的初始化项目信息
        String projectToken = returns.get(0).getProjectToken();
        String initStr = redisTemplate.opsForValue().get(projectToken);
        // 将用户填写的信息导入到要存到redis中的对象中--->ProjectStoreToRedisVo
        ProjectStoreToRedisVo redisVo = JSON.parseObject(initStr, ProjectStoreToRedisVo.class);
        List<TReturn> tReturns = new ArrayList<>();
        TReturn tReturn = new TReturn();
        for (ProjectReturnInfoVo returnInfoVo : returns) {
            BeanUtils.copyProperties(returnInfoVo, tReturn);
            tReturns.add(tReturn);
        }
        // 先保存到项目创建的对象中
        redisVo.setProjectReturns(tReturns);
        //先写入到redis
        String redisStr = JSON.toJSONString(redisVo);
        redisTemplate.opsForValue().set(projectToken, redisStr);
        //返回项目令牌
        return AppResponse.ok(projectToken);
    }


    @ApiOperation("第四步: 完成项目创建")
    @ApiImplicitParams({@ApiImplicitParam(name = "accessToken", value = "用户令牌"),
            @ApiImplicitParam(name = "projectToken", value = "项目令牌")})
    @PostMapping("/saveProjectInfo")
    public AppResponse<String> saveProjectInfo(String accessToken, String projectToken) {
        String memberIdStr = redisTemplate.opsForValue().get(accessToken);
        if (StringUtils.isEmpty(memberIdStr)){
            //用户id为空
            return AppResponse.ok("用户没有登录, 请登录后重试");
        }
        //用户登录了  先取出redis中的初始化项目信息
        String initStr = redisTemplate.opsForValue().get(projectToken);
        // 将用户填写的信息导入到要存到redis中的对象中--->ProjectStoreToRedisVo
        ProjectStoreToRedisVo redisVo = JSON.parseObject(initStr, ProjectStoreToRedisVo.class);
        //添加创建人id
        redisVo.setMemberId(Integer.parseInt(memberIdStr));
        //初始化项目状态
        redisVo.setStatus("0");
        //直接保存到数据库
        try {
            //保存成功
            projectCreateService.saveProjectInfo(redisVo);
            //删除redis
            //redisTemplate.delete(projectToken);
            return AppResponse.ok("项目创建成功");
        } catch (Exception e) {
            e.printStackTrace();
            return AppResponse.ok("项目创建失败");
        }

    }


}
