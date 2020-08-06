package cn.itcast.bos.service.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.bos.dao.take_delivery.WorkbillDao;
import cn.itcast.bos.domain.take_delivery.WorkBill;
import cn.itcast.bos.utils.MailUtils;

public class WorkBillJob {
	
	@Autowired
	private WorkbillDao dao;
	
	public void sendMail(){
//		当月的工单workbill信息	
		List<WorkBill> list =  dao.findByMonth();
		String content ="";
		for (WorkBill workBill : list) {
			content+=workBill.toString()+"<br>";
		}
		MailUtils.sendMail("当月工单信息", content, "itcast_server@sina.com");
		
	}

}
