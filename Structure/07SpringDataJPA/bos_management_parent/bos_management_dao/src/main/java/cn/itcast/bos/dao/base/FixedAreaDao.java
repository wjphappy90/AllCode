package cn.itcast.bos.dao.base;

import org.springframework.data.jpa.repository.JpaRepository;

import cn.itcast.bos.domain.base.FixedArea;

public interface FixedAreaDao extends JpaRepository<FixedArea, String> {

}
