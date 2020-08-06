package com.example.mybatisplusdemo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @package: com.example.mybatisplusdemo.controller
 * @description: 测试hello
 * @author: YixinCapital--shanchangqing
 * @create: 2019-01-07 18:19
 **/
@Slf4j
@RestController
@RequestMapping("/test")
public class TestHellp {
    @GetMapping("/hello")
    public String sayHello(@RequestParam("name") String name){
        return String.format("hello,%s",name);
    }

}
