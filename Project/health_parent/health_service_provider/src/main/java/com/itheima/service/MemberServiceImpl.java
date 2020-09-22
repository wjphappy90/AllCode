package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.pojo.Member;
import com.itheima.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员服务
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;
    //根据手机号查询会员信息
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    //新增会员
    public void add(Member member) {
        //如果用户设置了密码，需要对密码进行md5加密
        String password = member.getPassword();
        if(password != null){
            password = MD5Utils.md5(password);
            member.setPassword(password);
        }
        memberDao.add(member);
    }

    //根据月份查询会员数量
    public List<Integer> findMemberCountByMonths(List<String> months) {
        List<Integer> memberCounts = new ArrayList<>();
        if(months != null && months.size() > 0){
            for (String month : months) {//2019.01
                String endTime = month + ".31";
                Integer memberCount = memberDao.findMemberCountBeforeDate(endTime);
                memberCounts.add(memberCount);
            }
        }
        return memberCounts;
    }
}
