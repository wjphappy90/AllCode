package com.example.mybatisplusdemo.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.mybatisplusdemo.dto.TestUserDTO;
import com.example.mybatisplusdemo.entity.TestUser;
import com.example.mybatisplusdemo.mapper.TestUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author IT讲坛
 * @since 2019-01-08
 */
@Slf4j
@RestController
@RequestMapping("/testUser")
public class TestUserController {
    @Autowired
    private TestUserMapper testUserMapper;
    @PostMapping("/save")
    public void save(@RequestBody TestUserDTO testUserDTO){
        log.info("》》》》》》》》》》》》》》》》保存入参：{}",JSONObject.toJSONString(testUserDTO));
        TestUser testUser=new TestUser();
        BeanUtils.copyProperties(testUserDTO,testUser);
        int insert = testUserMapper.insert(testUser);
        log.info("入库条数：{}",insert);
        log.info("《《《《《《《《《《《《《《《《保存出参：{}",JSONObject.toJSONString(testUser));

    }

}
