package com.itheima.controller;

import com.itheima.constant.MessageConstant;
import com.itheima.constant.RedisMessageConstant;
import com.itheima.entity.Result;
import com.itheima.utils.SMSUtils;
import com.itheima.utils.ValidateCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validatecode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

    //体检预约发送验证码
    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){
        String code = ValidateCodeUtils.generateValidateCode(4).toString();
        System.out.println("验证码：" + code);
        try{
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code);
            //将验证码保存到redis，只保存5分钟   13812345678001=>3454  13812345678002=>3333
            jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_ORDER,300,code);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

    //手机号快速登录发送验证码
    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){
        String code = ValidateCodeUtils.generateValidateCode(6).toString();
        System.out.println("验证码：" + code);
        try{
            SMSUtils.sendShortMessage(SMSUtils.VALIDATE_CODE,telephone,code);
            //将验证码保存到redis，只保存5分钟   13812345678001=>3454  13812345678002=>3333
            jedisPool.getResource().setex(telephone + RedisMessageConstant.SENDTYPE_LOGIN,300,code);
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
}
