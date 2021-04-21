package com.chenlong.user.service.impl;

import com.chenlong.user.exception.UserExixtException;
import com.chenlong.user.mapper.TMemberAddressMapper;
import com.chenlong.user.mapper.TMemberMapper;
import com.chenlong.user.pojo.TMember;
import com.chenlong.user.pojo.TMemberAddress;
import com.chenlong.user.pojo.TMemberAddressExample;
import com.chenlong.user.pojo.TMemberExample;
import com.chenlong.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired(required = false)
    private TMemberMapper memberMapper;

    @Autowired(required = false)
    private TMemberAddressMapper addressMapper;

    /**
     *  用户注册
     * @param member
     */
    @Override
    public void register(TMember member) {
        //查询当前手机号是否已经注册
        TMemberExample example = new TMemberExample();
        TMemberExample.Criteria criteria = example.createCriteria();
        criteria.andLoginacctEqualTo(member.getLoginacct());
        List<TMember> tMembers = memberMapper.selectByExample(example);
        if (tMembers != null && tMembers.size() > 0){
            //说明已经注册过
            throw new UserExixtException();
        }
        //密码加密
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String pwd = encoder.encode(member.getUserpswd());
        member.setUserpswd(pwd);
        //未实名认证
        member.setAuthstatus("0");
        //个体账户
        member.setAccttype("2");
        //个人账号
        member.setUsertype("0");
        try {
            memberMapper.insert(member);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户登录
     * @param loginacct 登录账户(手机号)
     * @param userpswd  登录密码
     * @return
     */
    @Override
    public TMember login(String loginacct, String userpswd) {
        //创建密码加密的类
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        TMemberExample example = new TMemberExample();
        TMemberExample.Criteria criteria = example.createCriteria();
        criteria.andLoginacctEqualTo(loginacct);
        List<TMember> tMembers = memberMapper.selectByExample(example);

        if (tMembers != null && tMembers.size() == 1){
            //有这个用户  比较密码
            TMember member = tMembers.get(0);
            boolean matches = encoder.matches(userpswd, member.getUserpswd());
            if (matches){
                //密码正确
                return member;
            }
            //密码错误
            return null;
        }
        //没这个用户
        return null;
    }

    /**
     *  根据id获取用户信息
     * @param id
     * @return
     */
    @Override
    public TMember getUserbyId(Integer id) {
        return memberMapper.selectByPrimaryKey(id);
    }


    /**
     *  根据用户id获取用户地址信息
     * @param memberId
     * @return
     */
    @Override
    public List<TMemberAddress> getAddressByMemberId(Integer memberId){
        TMemberAddressExample example = new TMemberAddressExample();
        TMemberAddressExample.Criteria criteria = example.createCriteria();
        criteria.andMemberidEqualTo(memberId);
        return addressMapper.selectByExample(example);
    }

}
