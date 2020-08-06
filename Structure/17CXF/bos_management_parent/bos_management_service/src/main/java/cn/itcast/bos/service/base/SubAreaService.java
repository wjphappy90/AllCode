package cn.itcast.bos.service.base;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.SubArea;

public interface SubAreaService {

	public void save(SubArea model);

	public Page<SubArea> findByPage(Pageable pageable);

}
