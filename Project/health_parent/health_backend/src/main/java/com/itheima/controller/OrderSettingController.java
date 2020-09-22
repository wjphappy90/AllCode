package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderSettingService;
import com.itheima.utils.POIUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 预约设置管理
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    //文件上传（Excel）
    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile){
        try {
            List<String[]> list = POIUtils.readExcel(excelFile);
            List<OrderSetting> data = new ArrayList<>();
            if(list != null && list.size() > 0){
                for (String[] strings : list) {
                    String date = strings[0];
                    String number = strings[1];
                    OrderSetting orderSetting = new OrderSetting(new SimpleDateFormat("yyyy/MM/dd").parse(date),Integer.parseInt(number));
                    data.add(orderSetting);
                }
            }
            orderSettingService.add(data);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }

    //根据年月查询对应的预约设置信息
    @RequestMapping("/getOrderSettingByMonth")
    public Result getOrderSettingByMonth(String month){//2019-7
        try{
            List<Map<String,Object>> list = orderSettingService.getOrderSettingByMonth(month);
            return new Result(true, MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    //根据日期修改可预约人数
    @RequestMapping("/editNumberByOrderDate")
    public Result editNumberByOrderDate(@RequestBody OrderSetting orderSetting){
        try{
            orderSettingService.editNumberByOrderDate(orderSetting);
            return new Result(true, MessageConstant.ORDERSETTING_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ORDERSETTING_FAIL);
        }
    }
}
