package cn.itcast.erp.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.erp.dao.IEmpDao;
import cn.itcast.erp.dao.IOrdersDao;
import cn.itcast.erp.dao.ISupplierDao;
import cn.itcast.erp.entity.Orders;
import net.sf.jxls.exception.ParsePropertyException;
import net.sf.jxls.transformer.XLSTransformer;

public class SupplierTest {
	
	
	@Test
	public void testLogic() throws FileNotFoundException, IOException, ParsePropertyException, InvalidFormatException{
		String path = this.getClass().getResource("/").getPath();
		File template = new File(path, "excelTemplate.xlsx");
		if(!template.exists()){
			return;
		}
		ApplicationContext ac = new ClassPathXmlApplicationContext("classpath*:applicationContext*.xml");
		IOrdersDao ordersDao = (IOrdersDao)ac.getBean("ordersDao");
		Orders orders = ordersDao.get(5l);
		ISupplierDao supplierDao = (ISupplierDao)ac.getBean("supplierDao");
		IEmpDao empDao = (IEmpDao)ac.getBean("empDao");
		orders.setCreaterName(empDao.get(orders.getCreater()).getName());
		if(null != orders.getChecker()){
			orders.setCheckerName(empDao.get(orders.getChecker()).getName());
		}
		if(null != orders.getStarter()){
			orders.setStarterName(empDao.get(orders.getStarter()).getName());
		}
		if(null != orders.getEnder()){
			orders.setEnderName(empDao.get(orders.getEnder()).getName());
		}
		orders.setSupplierName(supplierDao.get(orders.getSupplieruuid()).getName());
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("orders", orders);
		XLSTransformer trans = new XLSTransformer();
		trans.transformXLS(path + File.separator + "excelTemplate.xls", data, "f:\\tt.xls");
		
	}
	

}
