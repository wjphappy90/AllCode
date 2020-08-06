package cn.itcast.crm.service.ws;

import java.util.List;

import javax.jws.WebService;

import cn.itcast.crm.domain.Customer;

@WebService
public interface CustomerService {
//	1、	查询未关联定区的所有客户--查询 customer总fixedAreaId为null的数据
	public List<Customer> findByFixedAreaIdIsNull();
	
//	2、	查询已关联指定定区的所有客户
	public List<Customer> findByFixedAreaId(String fixedAreaId);
//	3、	保存关联定区的客户
	public  void assignCustomers2FixedArea(String fixedAreaId,String customerIds);
//	4、	保存客户
	public  void save(Customer customer);
//	5、	根据电话号码查询客户
	public Customer findByTelephone(String telephone);
	
//	6、激活邮箱
	public void activeMail(String telephone);
	
//	7、根据邮箱和密码查询
	public Customer findByEmailAndPassword(String email,String password);
	
//	8、根据电话号码和密码查询
	public Customer findByTelephoneAndPassword(String telephone,String password);
//	9、根据地址查询
	public String findByAddress(String address);
}
