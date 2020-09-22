package com.itheima.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.dao.OrderDao;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据统计服务
 */

@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderDao orderDao;
    //查询运用数据

    /**
     reportDate:null,
     todayNewMember :0,
     totalMember :0,
     thisWeekNewMember :0,
     thisMonthNewMember :0,
     todayOrderNumber :0,
     todayVisitsNumber :0,
     thisWeekOrderNumber :0,
     thisWeekVisitsNumber :0,
     thisMonthOrderNumber :0,
     thisMonthVisitsNumber :0,
     hotSetmeal :[
     {name:'阳光爸妈升级肿瘤12项筛查（男女单人）体检套餐',setmeal_count:200,proportion:0.222},
     {name:'阳光爸妈升级肿瘤12项筛查体检套餐',setmeal_count:200,proportion:0.222}
     ]
     */
    public Map<String, Object> getBusinessReportData() throws Exception{
        //获得当前系统时间
        String today = DateUtils.parseDate2String(DateUtils.getToday());
        //动态获得本周一的日期
        String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        //动态获得本月第一天的日期
        String firstDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());

        //今日新增会员数
        Integer todayNewMember = memberDao.findMemberCountByDate(today);//select count(id) from t_member where regTime = today

        //总会员数据
        Integer totalMember = memberDao.findMemberTotalCount();

        //本周新增会员数
        Integer thisWeekNewMember = memberDao.findMemberCountAfterDate(thisWeekMonday);

        //本月新增会员数
        Integer thisMonthNewMember = memberDao.findMemberCountAfterDate(firstDay4ThisMonth);

        //今日预约数
        Integer todayOrderNumber = orderDao.findOrderCountByDate(today);

        //今日到诊数
        Integer todayVisitsNumber = orderDao.findVisitsCountByDate(today);

        //本周预约数
        Integer thisWeekOrderNumber = orderDao.findOrderCountAfterDate(thisWeekMonday);

        //本周到诊数
        Integer thisWeekVisitsNumber = orderDao.findVisitsCountAfterDate(thisWeekMonday);

        //本月预约数
        Integer thisMonthOrderNumber = orderDao.findOrderCountAfterDate(firstDay4ThisMonth);

        //本月到诊数
        Integer thisMonthVisitsNumber = orderDao.findVisitsCountAfterDate(firstDay4ThisMonth);

        //热门套餐
        List<Map> hotSetmeal = orderDao.findHotSetmeal();

        Map<String,Object> result = new HashMap<>();
        result.put("reportDate",today);
        result.put("todayNewMember",todayNewMember);
        result.put("totalMember",totalMember);
        result.put("thisWeekNewMember",thisWeekNewMember);
        result.put("thisMonthNewMember",thisMonthNewMember);
        result.put("todayOrderNumber",todayOrderNumber);
        result.put("thisWeekOrderNumber",thisWeekOrderNumber);
        result.put("thisMonthOrderNumber",thisMonthOrderNumber);
        result.put("todayVisitsNumber",todayVisitsNumber);
        result.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        result.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        result.put("hotSetmeal",hotSetmeal);

        return result;
    }
}
