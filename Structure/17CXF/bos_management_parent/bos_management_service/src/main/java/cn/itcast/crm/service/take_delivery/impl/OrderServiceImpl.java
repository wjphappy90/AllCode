package cn.itcast.crm.service.take_delivery.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.AreaDao;
import cn.itcast.bos.dao.base.FixedAreaDao;
import cn.itcast.bos.dao.base.SubAreaDao;
import cn.itcast.bos.dao.take_delivery.OrderDao;
import cn.itcast.bos.dao.take_delivery.WorkbillDao;
import cn.itcast.bos.domain.base.Area;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.domain.take_delivery.Order;
import cn.itcast.bos.domain.take_delivery.WorkBill;
import cn.itcast.bos.utils.SmsUtils;
import cn.itcast.crm.service.take_delivery.OrderService;
import cn.itcast.crm.service.ws.CustomerService;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

	@Autowired
	private OrderDao dao;
	@Autowired
	private AreaDao adao;
	@Autowired
	private FixedAreaDao fdao;
	@Autowired
	private WorkbillDao wdao;
	@Autowired
	private SubAreaDao sdao;
	@Autowired
	private CustomerService crmProxy;
	@Override
	public void save(Order order){
		Area sendArea = order.getSendArea();
		sendArea = adao.findByProvinceAndCityAndDistrict(sendArea.getProvince(),sendArea.getCity(),sendArea.getDistrict());
		order.setSendArea(sendArea);
//		保存订单
		dao.save(order);
//		基于CRM地址库完全匹配实现自动分单
		String fixedAreaId = crmProxy.findByAddress(order.getSendAddress());
		if(fixedAreaId!=null){
			FixedArea fixedArea = fdao.findOne(fixedAreaId);
			Set<Courier> couriers = fixedArea.getCouriers();
//			特殊说明从多个快递员中指定一个快递员是快递公司的核心算法
			for (Courier courier : couriers) {
				WorkBill workbill = new WorkBill();
				workbill.setAttachbilltimes(0); // 追单次数
				workbill.setBuildtime(new Date());// 工单生成时间
				workbill.setCourier(courier);
				workbill.setOrder(order);
				workbill.setPickstate("新单");//取件状态
				workbill.setRemark(order.getRemark());
				workbill.setSmsNumber("3232332");  //短信序号
				workbill.setType(" 新");// 工单类型 新,追,销
				wdao.save(workbill);
//				发送短信告知快递员
				System.out.println("请到"+order.getSendAddress()+"取件");
//				SmsUtils.sendSmsByWebService(courier.getTelephone(), "请到"+order.getSendAddress()+"取件");
				return;
			}
		}else{
//			基于分区关键字匹配实现自动分单
			String sendAddress =  order.getSendAddress();
			List<SubArea> list = sdao.findAll();
			for (SubArea subArea : list) {
				if(sendAddress.contains(subArea.getKeyWords())||sendAddress.contains(subArea.getAssistKeyWords())){
					FixedArea fixedArea = subArea.getFixedArea();
					Set<Courier> couriers = fixedArea.getCouriers();
//					特殊说明从多个快递员中指定一个快递员是快递公司的核心算法
					for (Courier courier : couriers) {
						WorkBill workbill = new WorkBill();
						workbill.setAttachbilltimes(0); // 追单次数
						workbill.setBuildtime(new Date());// 工单生成时间
						workbill.setCourier(courier);
						workbill.setOrder(order);
						workbill.setPickstate("新单");//取件状态
						workbill.setRemark(order.getRemark());
						workbill.setSmsNumber("3232332");  //短信序号
						workbill.setType(" 新");// 工单类型 新,追,销
						wdao.save(workbill);
//						发送短信告知快递员
						System.out.println("请到"+order.getSendAddress()+"取件");
//						SmsUtils.sendSmsByWebService(courier.getTelephone(), "请到"+order.getSendAddress()+"取件");
						return;
				}
			}
		}
	}
}
}
