package com.mikasa.dao;

import com.mikasa.pojo.User;

/**
 * 用户管理
 */
public interface UserDao {
    //1.根据用户名查询用户信息
    User findUserByUsername(String username);
}
