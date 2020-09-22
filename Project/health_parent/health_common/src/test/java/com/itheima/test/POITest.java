package com.itheima.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class POITest {
    //使用POI从Excel文件读取数据
    //@Test
    public void test1() throws Exception{
        //通过输入流加载指定的文件，封装成一个POI提供的Excel文件对象
        XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File("C:\\Users\\zhaoqx\\Desktop\\传智健康项目_广州111\\第5章\\资源\\poi.xlsx")));
        //读取第一个工作表中的数据
        XSSFSheet sheet = excel.getSheetAt(0);
        //遍历工作表获得每一行数据
        for (Row row : sheet) {
            System.out.println();
            //遍历行对象获得每一列，获得单元格对象
            for (Cell cell : row) {
                //获得单元格中的数据
                String value = cell.getStringCellValue();
                System.out.print(value + " ");
            }
        }
        //关闭资源
        excel.close();
    }

    //使用POI从Excel文件读取数据
    //@Test
    public void test2() throws Exception{
        //通过输入流加载指定的文件，封装成一个POI提供的Excel文件对象
        XSSFWorkbook excel = new XSSFWorkbook(new FileInputStream(new File("C:\\Users\\zhaoqx\\Desktop\\传智健康项目_广州111\\第5章\\资源\\poi.xlsx")));
        //读取第一个工作表中的数据
        XSSFSheet sheet = excel.getSheetAt(0);
        //获得最后一个行号，行号从0开始
        int lastRowNum = sheet.getLastRowNum();
        for(int i=0;i<=lastRowNum;i++){
            XSSFRow row = sheet.getRow(i);//根据行索引获取某一行
            short lastCellNum = row.getLastCellNum();
            for(int j=0;j<lastCellNum;j++){
                XSSFCell cell = row.getCell(j);//根据列索引获得每个单元格对象
                System.out.println(cell.getStringCellValue());
            }
        }
        //关闭资源
        excel.close();
    }

    //通过POI将数据写入到Excel文件中
    //@Test
    public void test3() throws Exception{
        //在内存中创建一个Excel文件对象
        XSSFWorkbook excel = new XSSFWorkbook();
        //在Excel文件中创建工作表
        XSSFSheet sheet = excel.createSheet("传智播客");
        //在工作表中创建行对象
        XSSFRow row = sheet.createRow(0);
        //在行中创建单元格对象
        row.createCell(0).setCellValue("姓名");
        row.createCell(1).setCellValue("地址");

        XSSFRow row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue("小明");
        row1.createCell(1).setCellValue("广州");

        XSSFRow row2 = sheet.createRow(2);
        row2.createCell(0).setCellValue("小王");
        row2.createCell(1).setCellValue("北京");

        //使用输出流将内存中的Excel写到磁盘
        OutputStream out = new FileOutputStream(new File("C:\\Users\\zhaoqx\\Desktop\\传智健康项目_广州111\\第5章\\资源\\test.xlsx"));
        excel.write(out);

        out.flush();
        out.close();
        excel.close();
    }
}
