package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.Result;
import com.itheima.service.MemberService;
import com.itheima.service.ReportService;
import com.itheima.service.SetmealService;
import com.itheima.utils.DateUtils;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * 统计报表
 */

@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    private MemberService memberService;
    @Reference
    private SetmealService setmealService;
    //获取会员数量统计数据
    @RequestMapping("/getMemberReport")
    public Result getMemberReport(){
        try{
            //动态获取过去1年的12个月，并封装到集合中
            List<String> months = new ArrayList<>();
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH,-12);//add方法用于计算
            for(int i = 0;i<12;i++){
                calendar.add(Calendar.MONTH,1);//add方法用于计算
                months.add(DateUtils.parseDate2String(calendar.getTime(),"yyyy.MM"));
            }
            //根据月份查询对应的会员数量
            List<Integer> memberCount = memberService.findMemberCountByMonths(months);

            Map<String,Object> data = new HashMap<>();
            data.put("months",months);
            data.put("memberCount",memberCount);
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS,data);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }

    //获取套餐占比统计数据
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport(){
        try{
            List<String> setmealNames = new ArrayList<>();//封装套餐名称集合
            List<Map> setmealCount = setmealService.getSetmealReport();//套餐名称和预约数量集合
            if(setmealCount != null && setmealCount.size() > 0){
                for (Map map : setmealCount) {
                    String name = (String) map.get("name");//套餐名称
                    setmealNames.add(name);
                }
            }

            Map<String,Object> map = new HashMap<>();
            map.put("setmealNames",setmealNames);
            map.put("setmealCount",setmealCount);
            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }

    @Reference
    private ReportService reportService;

    //获取运营统计数据
    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData(){
        try{
            Map<String,Object> map = reportService.getBusinessReportData();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS,map);
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    //导出运营数据到Excel并提供客户端下载
    @RequestMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest request, HttpServletResponse response){
        try{
            Map<String,Object> result = reportService.getBusinessReportData();

            //取出返回结果数据，准备将报表数据写入到Excel文件中
            String reportDate = (String) result.get("reportDate");
            Integer todayNewMember = (Integer) result.get("todayNewMember");
            Integer totalMember = (Integer) result.get("totalMember");
            Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) result.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) result.get("thisMonthVisitsNumber");
            List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");

            //动态获取Excel模板文件绝对磁盘路径
            String filePath = request.getSession().getServletContext().getRealPath("template") + File.separator + "report_template.xlsx";

            //基于POI在内存中创建一个Excel文件
            XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File(filePath)));

            XSSFSheet sheet = excel.getSheetAt(0);

            XSSFRow row = sheet.getRow(2);
            row.getCell(5).setCellValue(reportDate);//日期

            row = sheet.getRow(4);
            row.getCell(5).setCellValue(todayNewMember);//新增会员数（本日）
            row.getCell(7).setCellValue(totalMember);//总会员数

            row = sheet.getRow(5);
            row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

            row = sheet.getRow(7);
            row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
            row.getCell(7).setCellValue(todayVisitsNumber);//今日到诊数

            row = sheet.getRow(8);
            row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周到诊数

            row = sheet.getRow(9);
            row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月到诊数

            int rowNum = 12;
            for(Map map : hotSetmeal){//热门套餐
                String name = (String) map.get("name");
                Long setmeal_count = (Long) map.get("setmeal_count");
                BigDecimal proportion = (BigDecimal) map.get("proportion");
                row = sheet.getRow(rowNum ++);
                row.getCell(4).setCellValue(name);//套餐名称
                row.getCell(5).setCellValue(setmeal_count);//预约数量
                row.getCell(6).setCellValue(proportion.doubleValue());//占比
            }

            //创建输出流，用于从服务器写数据到客户端浏览器
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/vnd.ms-excel");
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
            excel.write(out);

            out.flush();
            out.close();
            excel.close();

            return null;
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    //导出运营数据到PDF文件并提供客户端下载
    @RequestMapping("/exportBusinessReport4PDF")
    public Result exportBusinessReport4PDF(HttpServletRequest request, HttpServletResponse response){
        try{
            Map<String,Object> result = reportService.getBusinessReportData();

            //取出返回结果数据，准备将报表数据写入到Excel文件中
            List<Map> hotSetmeal = (List<Map>) result.get("hotSetmeal");

            //动态获取pdf模板文件绝对磁盘路径
            String jrxmlPath = request.getSession().getServletContext().getRealPath("template") + File.separator + "health_business3.jrxml";
            String jasperPath = request.getSession().getServletContext().getRealPath("template") + File.separator + "health_business3.jasper";

            //编译模板
            JasperCompileManager.compileReportToFile(jrxmlPath, jasperPath);

            //填充数据---使用JavaBean数据源方式填充
            JasperPrint jasperPrint =
                    JasperFillManager.fillReport(jasperPath,result,
                            new JRBeanCollectionDataSource(hotSetmeal));

            //创建输出流，用于从服务器写数据到客户端浏览器
            ServletOutputStream out = response.getOutputStream();
            response.setContentType("application/pdf");
            response.setHeader("content-Disposition", "attachment;filename=report.pdf");

            //输出文件
            JasperExportManager.exportReportToPdfStream(jasperPrint,out);

            out.flush();
            out.close();

            return null;
        }catch (Exception e){
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }
}
