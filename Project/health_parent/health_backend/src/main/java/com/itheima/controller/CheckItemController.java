package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 检查项管理
 */

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference
    private CheckItemService checkItemService;
    //新增
    @RequestMapping("/add")
    public Result add(@RequestBody CheckItem checkItem){
        try{
            checkItemService.add(checkItem);//发送请求
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }

    //分页查询
    @RequestMapping("/findPage")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean){
        return checkItemService.findPage(queryPageBean);
    }

    //根据id删除检查项
    @RequestMapping("/delete")
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    public Result delete(Integer id){
        try{
            checkItemService.delete(id);//发送请求
            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
        }catch (Exception e){
            String message = e.getMessage();
            e.printStackTrace();
            return new Result(false, message);
        }
    }

    //根据id查询检查项
    @RequestMapping("/findById")
    public Result findById(Integer id){
        try{
            CheckItem checkItem = checkItemService.findById(id);//发送请求
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    //编辑
    @RequestMapping("/edit")
    public Result edit(@RequestBody CheckItem checkItem){
        try{
            checkItemService.edit(checkItem);//发送请求
            return new Result(true, MessageConstant.EDIT_CHECKITEM_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }

    //查询所有检查项
    @RequestMapping("/findAll")
    public Result findAll(){
        try{
            List<CheckItem> list = checkItemService.findAll();//发送请求
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
    //根据检查组id查询关联的检查项id
    @RequestMapping("/findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(Integer checkgroupId){
        try{
            List<Integer> list = checkItemService.findCheckItemIdsByCheckGroupId(checkgroupId);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
}
