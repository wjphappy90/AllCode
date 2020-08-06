package cn.itcast.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.system.Menu;

public interface MenuDao extends JpaRepository<Menu, Integer> {

	List<Menu> findByParentMenuIsNull();

	@Query("select distinct m from Menu m join m.roles r join r.users u where u.id=?")
	List<Menu> findByUser(Integer id);

}
