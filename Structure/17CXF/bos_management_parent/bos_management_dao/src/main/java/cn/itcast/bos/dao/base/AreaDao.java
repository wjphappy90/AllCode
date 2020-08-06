package cn.itcast.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.bos.domain.base.Area;

public interface AreaDao extends JpaRepository<Area, String> {

	Area findByProvinceAndCityAndDistrict(String province, String city, String district);

}
