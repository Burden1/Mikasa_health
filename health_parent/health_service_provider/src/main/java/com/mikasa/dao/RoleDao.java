package com.mikasa.dao;

import com.mikasa.pojo.Role;

import java.util.Set;

/**
 * 角色管理
 */
public interface RoleDao {
    //1.根据用户id查询角色集合
    Set<Role> findRolesByUserId(Integer userId);
}
