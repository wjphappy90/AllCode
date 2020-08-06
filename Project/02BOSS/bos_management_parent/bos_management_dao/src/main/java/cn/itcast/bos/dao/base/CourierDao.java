package cn.itcast.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.base.Courier;

public interface CourierDao extends JpaRepository<Courier, Integer> ,JpaSpecificationExecutor<Courier>{

	@Query("update Courier set deltag=1 where id=?")
	@Modifying
	public void updateDeltag(int parseInt);

}
