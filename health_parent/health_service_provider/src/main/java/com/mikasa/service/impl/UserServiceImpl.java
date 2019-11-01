package com.mikasa.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.mikasa.constant.MessageConstant;
import com.mikasa.dao.PeimissionDao;
import com.mikasa.dao.RoleDao;
import com.mikasa.dao.UserDao;
import com.mikasa.entity.Result;
import com.mikasa.pojo.Permission;
import com.mikasa.pojo.Role;
import com.mikasa.pojo.User;
import com.mikasa.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * 用户管理
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PeimissionDao peimissionDao;
    //根据用户名查询数据库获取用户信息和关联的角色信息，同时需要查询角色关联的权限信息
    @Override
    public User findUserByUsername(String username) {
        //1.根据用户名查询用户
        User user = userDao.findUserByUsername(username);
        //用户不存在
        if (user == null){
            return null;
        }
        //2.根据用户id查询角色集合
        Integer userId = user.getId();
        Set<Role> roles = roleDao.findRolesByUserId(userId);
        if (roles != null && roles.size() >0){
            for (Role role : roles){
                Integer roleId = role.getId();
                //3.根据角色id查询拥有权限
                Set<Permission> permissions = peimissionDao.findPermissionsByRoleId(roleId);
                if (permissions != null && permissions.size()>0){
                    role.setPermissions(permissions);
                }
            }
            user.setRoles(roles);
        }
        return user;
    }
}
