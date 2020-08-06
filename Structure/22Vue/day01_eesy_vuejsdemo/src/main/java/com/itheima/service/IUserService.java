package com.itheima.service;

import com.itheima.domain.User;

import java.util.List;

/**
 * 用户的业务层接口
 * @author 黑马程序员
 * @Company http://www.ithiema.com
 */
public interface IUserService {

    /**
     * 查询用户列表
     */
    List<User> findAll();

    /**
     * 根据id查询
     * @param userId
     * @return
     */
    User findById(Integer userId);

    /**
     * 更新用户
     * @param user
     */
    void updateUser(User user);
}
