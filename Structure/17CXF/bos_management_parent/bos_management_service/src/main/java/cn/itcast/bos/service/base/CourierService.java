package cn.itcast.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import cn.itcast.bos.domain.base.Courier;

public interface CourierService {

	public void save(Courier model);

	public Page<Courier> findByPage(Pageable pageable);

	public void deleteBatch(String ids);

	public Page<Courier> findByPage(Specification specification, Pageable pageable);

	public List<Courier> findAll();

}
