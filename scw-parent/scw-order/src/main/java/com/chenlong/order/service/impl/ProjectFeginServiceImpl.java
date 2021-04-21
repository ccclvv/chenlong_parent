package com.chenlong.order.service.impl;


import com.chenlong.common.response.AppResponse;
import com.chenlong.order.pojo.TReturn;
import com.chenlong.order.service.ProjectFeginService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
//这里是熔断虚拟数据类  一旦远程调用失败  这个类代替响应虚拟数据给调用者
public class ProjectFeginServiceImpl implements ProjectFeginService {
    @Override
    public AppResponse<List<TReturn>> getReturnList(Integer projectId) {
        AppResponse<List<TReturn>> resp = AppResponse.fail(null);
        resp.setMsg("远程调用,获取回报集合失败!");
        return resp;
    }

    @Override
    public AppResponse<TReturn> getReturnById(Integer returnId) {
        AppResponse<TReturn> resp = AppResponse.fail(null);
        resp.setMsg("远程调用,获取回报数据失败!");
        return resp;
    }
}
