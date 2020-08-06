package cn.itcast.crm.service.take_delivery;

import javax.jws.WebService;

import cn.itcast.bos.domain.take_delivery.Order;

@WebService
public interface OrderService {
	public void save(Order order);
}
