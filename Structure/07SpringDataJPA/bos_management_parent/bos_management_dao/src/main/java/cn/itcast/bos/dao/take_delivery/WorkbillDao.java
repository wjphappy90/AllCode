package cn.itcast.bos.dao.take_delivery;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.take_delivery.WorkBill;

public interface WorkbillDao extends JpaRepository<WorkBill, Integer> {

	@Query(value="select * from T_WORK_BILL t where to_char(t.c_buildtime,'yyyy-mm')= to_char( sysdate,'yyyy-mm')"
			,nativeQuery=true)
	List<WorkBill> findByMonth();

}
