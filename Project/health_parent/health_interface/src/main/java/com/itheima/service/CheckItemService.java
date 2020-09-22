package com.itheima.service;

import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.pojo.CheckItem;

import java.util.List;

public interface CheckItemService {
    public void add(CheckItem checkItem);
    public PageResult findPage(QueryPageBean queryPageBean);
    public void delete(Integer id);
    public CheckItem findById(Integer id);
    public void edit(CheckItem checkItem);
    public List<CheckItem> findAll();
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer checkgroupId);
}
