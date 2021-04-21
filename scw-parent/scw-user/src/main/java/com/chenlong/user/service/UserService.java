package com.chenlong.user.service;

import com.chenlong.common.response.AppResponse;
import com.chenlong.user.pojo.TMember;
import com.chenlong.user.pojo.TMemberAddress;
import com.chenlong.user.vo.resp.UserRespVo;

import java.util.List;

public interface UserService {

    /**
     *  用户注册
     * @param member
     */
    public void register(TMember member);

    /**
     *  用户登录
     * @param loginacct 登录账户(手机号)
     * @param userpswd  登录密码
     * @return
     */
    public TMember login(String loginacct, String userpswd);

    /**
     *  根据id获取用户信息
     * @param id
     * @return
     */
    public TMember getUserbyId(Integer id);

    /**
     *  根据用户id获取用户地址信息
     * @param memberId
     * @return
     */
    public List<TMemberAddress> getAddressByMemberId(Integer memberId);

}
