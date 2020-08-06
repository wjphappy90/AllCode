package cn.itcast.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.base.Standard;

public interface StandardDao extends JpaRepository<Standard, Integer> {

	public Standard findByName(String string);

	public List<Standard> findByOperator(String string);

	public List<Standard> findByOperatorLike(String string);

	public List<Standard> findByOperatorAndName(String string, String string2);

	public List<Standard> findByOperatorLikeOrNameLike(String string, String string2);

//	@Query("from Standard where operator=?") //jpql语句
	@Query(value="select * from t_Standard where c_operator=?",nativeQuery=true) //纯sql语句查询
	public List<Standard> findByXXXXX(String string);

	@Query("update Standard set operator=? where id=?")
	@Modifying   //需要修改数据时需要加 @Modifying
	public void updateOperator(String string, int i);

}
