package cn.itcast.crm.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.crm.domain.Customer;

public interface CustomerDao extends JpaRepository<Customer, Integer> {

	List<Customer> findByFixedAreaIdIsNull();

	List<Customer> findByFixedAreaId(String fixedAreaId);
	@Query("update Customer set fixedAreaId=? where id=?")
	@Modifying
	void updateFixedAreaId(String fixedAreaId, int parseInt);
	@Query("update Customer set fixedAreaId = null where fixedAreaId=?") 
	@Modifying
	void updateFixedAreaIdIsNull(String fixedAreaId);

	Customer findByTelephone(String telephone);

	@Query("update Customer set type=1 where telephone=?")
	@Modifying
	void updateType(String telephone);

	Customer findByEmailAndPassword(String email, String password);

	Customer findByTelephoneAndPassword(String telephone, String password);

	List<Customer> findByAddress(String address);
}
