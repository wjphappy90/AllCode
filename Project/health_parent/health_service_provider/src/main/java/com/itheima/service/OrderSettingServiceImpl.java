package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.OrderSettingDao;
import com.itheima.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预约设置服务
 */

@Service(interfaceClass = OrderSettingService.class)
@Transactional
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    //批量导入预约设置信息
    public void add(List<OrderSetting> list) {
        if(list != null && list.size() > 0){
            for (OrderSetting orderSetting : list) {
                //判断当前日期是否已经进行了设置
                long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
                if(count > 0){
                    //如果已经设置，执行更新操作
                    orderSettingDao.editNumberByOrderDate(orderSetting);
                }else{
                    //如果没有设置，执行插入操作
                    orderSettingDao.add(orderSetting);
                }
            }
        }
    }

    public List<Map<String, Object>> getOrderSettingByMonth(String month) {//2019-7
        String begin = month + "-1";//2019-7-1
        String end = month + "-31";//2019-7-31
        Map<String,String> map = new HashMap<>();
        map.put("begin",begin);
        map.put("end",end);
        List<OrderSetting> list = orderSettingDao.getOrderSettingByMonth(map);
        List<Map<String, Object>> data = new ArrayList<>();
        for (OrderSetting orderSetting : list) {
            Map<String, Object> orderSettingData = new HashMap<>();
            orderSettingData.put("date",orderSetting.getOrderDate().getDate());
            orderSettingData.put("number",orderSetting.getNumber());
            orderSettingData.put("reservations",orderSetting.getReservations());
            data.add(orderSettingData);
        }
        return data;
    }

    public void editNumberByOrderDate(OrderSetting orderSetting) {
        long count = orderSettingDao.findCountByOrderDate(orderSetting.getOrderDate());
        if(count > 0){
            //如果已经设置，执行更新操作
            orderSettingDao.editNumberByOrderDate(orderSetting);
        }else{
            //如果没有设置，执行插入操作
            orderSettingDao.add(orderSetting);
        }
        //orderSettingDao.editNumberByOrderDate(orderSetting);
    }
}
