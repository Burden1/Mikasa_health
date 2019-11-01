package com.mikasa.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.mikasa.dao.MemberDao;
import com.mikasa.pojo.Member;
import com.mikasa.service.MemberService;
import com.mikasa.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员管理
 */
@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService{
    @Autowired
    private MemberDao memberDao;
    //1.根据电话查询会员
    @Override
    public Member findMemberByTel(String telephone) {
        return memberDao.findMemberByTel(telephone) ;
    }

    //2.添加会员
    @Override
    public void add(Member member) {
        String password = member.getPassword();
        if (password != null){
            //为了安全，使用md5进行加密
            password = MD5Utils.md5(password);
            member.setPassword(password);
        }
        memberDao.add(member);
    }

    //3.根据月份查询数量
    @Override
    public List<Integer> findMemberCountByMonths(List<String> months) {
        List<Integer> memberCount = new ArrayList<>();
        for (String month :months){
            String date = month+".31";//2019.11.31
            Integer count = memberDao.findMemberCountByMonths(date);
            memberCount.add(count);
        }
        return memberCount;
    }
}
