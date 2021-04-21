package com.chenlong.order.controller;

import com.chenlong.common.response.AppResponse;
import com.chenlong.order.pojo.TOrder;
import com.chenlong.order.service.OrderService;
import com.chenlong.order.vo.req.OrderInfoSubmitVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "订单数据的处理")
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @ApiOperation("保存订单,获取支付金额")
    @PostMapping("/saveOrder")
    public AppResponse<TOrder> saveOrder(OrderInfoSubmitVo orderVo){
        try {
            TOrder order = orderService.saveOrder(orderVo);
            return AppResponse.ok(order);
        } catch (Exception e) {
            e.printStackTrace();
            AppResponse<TOrder> resp = AppResponse.fail(null);
            resp.setMsg(e.getMessage());
            return resp;
        }
    }

}
