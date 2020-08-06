package com.example.mybatisplusdemo.service.impl;

import com.example.mybatisplusdemo.entity.TestUser;
import com.example.mybatisplusdemo.mapper.TestUserMapper;
import com.example.mybatisplusdemo.service.ITestUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author IT讲坛
 * @since 2019-01-08
 */
@Service
public class TestUserServiceImpl extends ServiceImpl<TestUserMapper, TestUser> implements ITestUserService {

}
