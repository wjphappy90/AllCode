package cn.itcast.bos.action;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.take_delivery.Order;
import cn.itcast.crm.service.take_delivery.impl.OrderService;
@Controller
@Scope("prototype")
@Namespace("/")
@ParentPackage("struts-default")
public class OrderAction extends BaseAction<Order> {

	private String sendAreaInfo;
	public void setSendAreaInfo(String sendAreaInfo) {
		this.sendAreaInfo = sendAreaInfo;
	}
	
	@Autowired
	private OrderService bosProxy;

	@Action("orderAction_add")
	public void add(){
//		调用webservice服务完成订单的保存
		String[] split = sendAreaInfo.split("/");
		Area sendArea = new Area();
		sendArea.setProvince(split[0]);
		sendArea.setCity(split[1]);
		sendArea.setDistrict(split[2]);
		model.setSendArea(sendArea);
		bosProxy.save(model);
	}
	
	
}
