package cn.itcast.crm.service.ws;

import java.util.List;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.crm.dao.CustomerDao;
import cn.itcast.crm.domain.Customer;

@Service
@Transactional
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	private CustomerDao dao;
	@Override
	public List<Customer> findByFixedAreaIdIsNull() {
		return dao.findByFixedAreaIdIsNull();
	}

	@Override
	public List<Customer> findByFixedAreaId(String fixedAreaId) {
		return dao.findByFixedAreaId(fixedAreaId);
	}

	@Override
	public void assignCustomers2FixedArea(String fixedAreaId, String customerIds) {
		String[] ids = customerIds.split(",");
		dao.updateFixedAreaIdIsNull(fixedAreaId); //把原来关联此定区的所有客户清空
		for (int i = 0; i < ids.length; i++) {
			dao.updateFixedAreaId(fixedAreaId,Integer.parseInt(ids[i]));
		}
	}

	@Override
	public void save(Customer customer) {
		Md5Hash md5 = new Md5Hash(customer.getPassword(), "suyunkuaidi", 10);
//		customer.getPassword()
		customer.setPassword(md5.toString());
		dao.save(customer);
	}

	@Override
	public Customer findByTelephone(String telephone) {
		return dao.findByTelephone(telephone);
	}

	@Override
	public void activeMail(String telephone) {
		dao.updateType(telephone);
	}

	@Override
	public Customer findByEmailAndPassword(String email, String password) {
		Md5Hash md5 = new Md5Hash(password, "suyunkuaidi", 10);
		return dao.findByEmailAndPassword(email,md5.toString());
	}

	@Override
	public Customer findByTelephoneAndPassword(String telephone, String password) {
		Md5Hash md5 = new Md5Hash(password, "suyunkuaidi", 10);
		return dao.findByTelephoneAndPassword(telephone,md5.toString());
	}

	@Override
	public String findByAddress(String address) {
		 List<Customer> list = dao.findByAddress(address);
		 if(list!=null&&list.size()!=0){
			 return list.get(0).getFixedAreaId();
		 }
		 return null;
	}

}
