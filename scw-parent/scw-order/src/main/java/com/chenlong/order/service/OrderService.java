package com.chenlong.order.service;

import com.chenlong.order.pojo.TOrder;
import com.chenlong.order.vo.req.OrderInfoSubmitVo;

public interface OrderService {

    /**
     * 保存订单数据到数据库  并且计算支付金额
     * @param orderVo
     * @return
     */
    public TOrder saveOrder(OrderInfoSubmitVo orderVo);

}
