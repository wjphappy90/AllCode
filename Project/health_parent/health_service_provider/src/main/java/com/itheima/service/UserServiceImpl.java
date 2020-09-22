package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.PermissionDao;
import com.itheima.dao.RoleDao;
import com.itheima.dao.UserDao;
import com.itheima.pojo.Permission;
import com.itheima.pojo.Role;
import com.itheima.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

/**
 * 用户服务
 */
@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;
    //根据用户名查询用户信息,包括用户的角色和角色关联的权限
    public User findByUsername(String username) {
        User user = userDao.findByUsername(username);//根据用户名查询用户表
        if(user == null){
            return null;
        }
        Integer userId = user.getId();
        //根据用户id查询关联的角色
        Set<Role> roles = roleDao.findByUserId(userId);
        if(roles != null && roles.size() > 0){
            //遍历角色集合，查询每个角色关联的权限
            for (Role role : roles) {
                Integer roleId = role.getId();//角色id
                //根据角色id查询关联的权限
                Set<Permission> permissions = permissionDao.findByRoleId(roleId);
                if(permissions != null && permissions.size() > 0){
                    //角色关联权限集合
                    role.setPermissions(permissions);
                }
            }
            //用户关联角色集合
            user.setRoles(roles);
        }

        return user;
    }
}
