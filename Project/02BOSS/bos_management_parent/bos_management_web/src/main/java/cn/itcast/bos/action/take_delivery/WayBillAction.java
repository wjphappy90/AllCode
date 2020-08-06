package cn.itcast.bos.action.take_delivery;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.action.base.BaseAction;
import cn.itcast.bos.domain.take_delivery.WayBill;
import cn.itcast.crm.service.take_delivery.WayBillService;
@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
public class WayBillAction extends BaseAction<WayBill> {

	@Autowired
	private WayBillService service;
	
	
	@Action("wayBillAction_save")
	public void save(){
		try {
			service.save(model);
			javaToJson(ajaxReturn(true, "操作成功"), null);
		} catch (Exception e) {
			javaToJson(ajaxReturn(false, "操作失败"), null);
			e.printStackTrace();
		}
	}
	
	@Action("wayBillAction_delete")
	public void delete(){
		System.out.println("SSSSSSSSSSSSSSSSSSSSS");
	}
}
