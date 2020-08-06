package cn.itcast.bos.action.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.service.base.FixedAreaService;
import cn.itcast.crm.service.ws.Customer;
import cn.itcast.crm.service.ws.CustomerService;
@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
public class FixedAreaAction extends BaseAction<FixedArea> {
	
	@Autowired
	private FixedAreaService service;
	
	private String ids;//分区的ids
	public void setIds(String ids) {
		this.ids = ids;
	}
	private Integer courierId; //快递员的id
	private Integer takeTimeId;//收派时间的id
	public void setCourierId(Integer courierId) {
		this.courierId = courierId;
	}
	public void setTakeTimeId(Integer takeTimeId) {
		this.takeTimeId = takeTimeId;
	}
	@Action("fixedAreaAction_associationCourierToFixedArea")
	public void associationCourierToFixedArea(){
		try {
			service.associationCourierToFixedArea(model.getId(),courierId,takeTimeId);
			javaToJson(ajaxReturn(true, "操作成功"), null);
		} catch (Exception e) {
			javaToJson(ajaxReturn(false, "操作失败"), null);
			e.printStackTrace();
		}
	}
	
	
	@Action("fixedAreaAction_save")
	public void save(){
		try {
			service.save(model,ids);
			javaToJson(ajaxReturn(true, "操作成功"), null);
		} catch (Exception e) {
			javaToJson(ajaxReturn(false, "操作失败"), null);
			e.printStackTrace();
		}
	}
	
	@Action("fixedAreaAction_findByPage")
	public void findByPage(){
		Pageable pageable = new PageRequest(page-1, rows);
		Page<FixedArea> page = service.findByPage(pageable);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("total", page.getTotalElements());
		map.put("rows", page.getContent());
		javaToJson(map, new String[]{"subareas","couriers"});
	}
	
	@Autowired
	private  CustomerService crmProxy;
	//查询未关联定区的所有客户
	@Action("fixedAreaAction_findCustomerByFixedAreaIdIsNull")
	public void findCustomerByFixedAreaIdIsNull(){
		List<Customer> list = crmProxy.findByFixedAreaIdIsNull();
		javaToJson(list, null);
	}
	//查询关联指定定区的所有客户
	@Action("fixedAreaAction_findCustomerByFixedAreaId")
	public void findCustomerByFixedAreaId(){
		List<Customer> list = crmProxy.findByFixedAreaId(model.getId());
		javaToJson(list, null);
	}
	private String customerIds;
	public void setCustomerIds(String customerIds) {
		this.customerIds = customerIds;
	}

	@Action("fixedAreaAction_assignCustomers2FixedArea")
	public void assignCustomers2FixedArea(){
//		id  customerIds
		try {
			crmProxy.assignCustomers2FixedArea(model.getId(), customerIds);
			javaToJson(ajaxReturn(true, "操作成功"), null);
		} catch (Exception e) {
			javaToJson(ajaxReturn(false, "操作失败"), null);
			e.printStackTrace();
		}
	}

	
}
