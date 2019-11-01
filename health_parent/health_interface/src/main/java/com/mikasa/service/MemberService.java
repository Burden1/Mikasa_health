package com.mikasa.service;

import com.mikasa.pojo.Member;

import java.util.List;

/**
 * 会员管理
 */
public interface MemberService {
    //1.根据手机号查找会员
    Member findMemberByTel(String telephone);
    //2.添加会员
    void add(Member member);
    //3.根据月份查询会员数量
    List<Integer> findMemberCountByMonths(List<String> months);
}
