package com.itheima.service;

import com.itheima.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

public interface OrderSettingService {
    public void add(List<OrderSetting> list);
    public List<Map<String,Object>> getOrderSettingByMonth(String month);
    public void editNumberByOrderDate(OrderSetting orderSetting);
}
