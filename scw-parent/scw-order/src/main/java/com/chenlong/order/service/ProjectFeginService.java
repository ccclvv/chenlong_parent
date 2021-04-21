package com.chenlong.order.service;

import com.chenlong.common.response.AppResponse;
import com.chenlong.order.pojo.TReturn;
import com.chenlong.order.service.impl.ProjectFeginServiceImpl;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

//远程调用
@FeignClient(value = "SCW-PROJECT", fallback = ProjectFeginServiceImpl.class)
public interface ProjectFeginService {

    //根据项目id获取回报信息
    @GetMapping("/projectInfo/getReturns/{projectId}")
    public AppResponse<List<TReturn>> getReturnList(@PathVariable("projectId") Integer projectId);

    //根据回报id获取回报信息
    @GetMapping("/projectInfo/getReturn/{returnId}")
    public AppResponse<TReturn> getReturnById(@PathVariable("returnId") Integer returnId);

}
