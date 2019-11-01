package com.mikasa.service;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mikasa.pojo.Permission;
import com.mikasa.pojo.Role;
import com.mikasa.pojo.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 权限控制 实现认证和授权
 */
@Component
public class SpringSecurityUserService implements UserDetailsService {
    @Reference
    private UserService userService;
    //1.根据用户名查询数据库获取用户信息
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //1.根据用户名查询用户
        User user = userService.findUserByUsername(username);
        System.out.println(user);
        //用户不存在 返回null
        if (user == null){
            return null;
        }
        List<GrantedAuthority> list = new ArrayList<>();

        //2.动态为当前用户授权
        Set<Role> roles = user.getRoles();
        //遍历角色集合，为用户授予角色
        for (Role role :roles){
            System.out.println("角色"+role);
            list.add(new SimpleGrantedAuthority(role.getKeyword()));
            //遍历权限集合，为角色授权
            Set<Permission> permissions = role.getPermissions();
            for (Permission permission : permissions){
                System.out.println("权限"+permission);
                list.add(new SimpleGrantedAuthority(permission.getKeyword()));
            }
        }
        org.springframework.security.core.userdetails.User securityUser = new org.springframework.security.core.userdetails.User(username,user.getPassword(),list);
        System.out.println(securityUser);
        return securityUser;
    }
}
