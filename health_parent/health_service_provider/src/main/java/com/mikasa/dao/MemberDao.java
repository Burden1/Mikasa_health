package com.mikasa.dao;

import com.mikasa.pojo.Member;

/**
 * 会员管理
 */
public interface MemberDao {
    //1.根据手机号查询会员
    Member findMemberByTel(String telephone);
    //2.添加会员
    void add(Member member);
    //3.根据指定月份查询会员数量
    Integer findMemberCountByMonths(String month);
    //4.查询当日会员人数
    Integer findMemberCountByDate(String today);
    //5.查询会员总人数
    Integer findMemberTotalCount();
    //6.根据日期统计会员数，统计指定日期之后的会员数
    Integer findMemberCountAfterDate(String thisWeekMonday);
}
