package com.chenlong.order.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.chenlong.common.response.AppResponse;
import com.chenlong.common.utils.DateUtil;
import com.chenlong.common.utils.IdWorker;
import com.chenlong.order.enums.OrderStatusEnumes;
import com.chenlong.order.mapper.TOrderMapper;
import com.chenlong.order.pojo.TOrder;
import com.chenlong.order.pojo.TReturn;
import com.chenlong.order.service.OrderService;
import com.chenlong.order.service.ProjectFeginService;
import com.chenlong.order.vo.req.OrderInfoSubmitVo;
import com.sun.xml.internal.bind.v2.model.core.ID;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired(required = false)
    private TOrderMapper orderMapper;

    @Autowired
    private StringRedisTemplate redisTemplate;

    //远程调用  获取回报的金额
    @Resource
    private ProjectFeginService feginService;

    /**
     * 保存订单数据到数据库  并且计算支付金额
     * @param orderVo
     * @return
     */
    @Override
    public TOrder saveOrder(OrderInfoSubmitVo orderVo) {
        TOrder order = new TOrder();
        //先判断用户是否登录
        String userId = redisTemplate.opsForValue().get(orderVo.getAccessToken());
        if (StringUtils.isEmpty(userId)){
            //用户没有登录
            throw new RuntimeException("用户没有登录");
        }
        order.setMemberid(Integer.parseInt(userId));
        //添加数据到order
        BeanUtils.copyProperties(orderVo, order);
        //添加剩余字段
        //订单编号  雪花算法
        IdWorker idWorker = new IdWorker();
        order.setOrdernum(idWorker.nextId()+"");
        //订单状态
        order.setStatus(OrderStatusEnumes.UNPAY.getCode());
        //订单创建时间
        order.setCreatedate(DateUtil.getFormateNow());
        //支付金额   计算获得  回报金额 * 数量 + 运费
        //远程调用
        AppResponse<TReturn> resp = feginService.getReturnById(order.getReturnid());
        if (resp == null || resp.getData() == null) {
            throw new RuntimeException("远程调用失败");
        }
        int money = resp.getData().getSupportmoney() * order.getRtncount() + resp.getData().getFreight();
        order.setMoney(money);
        //保存到数据库
        orderMapper.insert(order);
        return order;
    }
}
