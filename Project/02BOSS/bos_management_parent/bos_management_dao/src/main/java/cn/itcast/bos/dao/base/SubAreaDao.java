package cn.itcast.bos.dao.base;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cn.itcast.bos.domain.base.SubArea;

public interface SubAreaDao extends JpaRepository<SubArea, String> {

//	select a.c_district,count(*) from T_SUB_AREA sa , t_area a where sa.c_area_id=a.c_id
//	group by a.c_district
	@Query("select a.district,count(*) from SubArea sa join sa.area a group by a.district ")
	public List showChart();

}
