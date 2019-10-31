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
}
