package cn.itcast.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.Standard;

public interface StandardService {

	public void updateOperator(String string, int i);

	public void save(Standard model);

	public Page<Standard> findByPage(Pageable pageable);

	public List<Standard> findAll();

}
