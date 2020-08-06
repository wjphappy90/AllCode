package cn.itcast.bos.dao.system;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.system.Role;

public interface RoleDao extends JpaRepository<Role, Integer> {
//	select * from t_role r ,t_user_role ur , t_user u where 
//	 r.c_id=ur.c_role_id and ur.c_user_id=u.c_id and u.c_id=181
	
	@Query("select r from Role r join r.users u where u.id=?")
	List<Role> findByUser(Integer id);

}
