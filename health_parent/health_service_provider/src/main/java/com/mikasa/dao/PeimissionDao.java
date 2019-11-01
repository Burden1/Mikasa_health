package com.mikasa.dao;

import com.mikasa.pojo.Permission;

import java.util.Set;

/**
 * 权限管理
 */
public interface PeimissionDao {
    //1.通过权限id查询权限集合
    Set<Permission> findPermissionsByRoleId(Integer roleId);
}
