package cn.itcast.bos.service.base;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import cn.itcast.bos.domain.base.Area;

public interface AreaService {

	public void save(List<Area> list);

	public Page<Area> findByPage(Pageable pageable);

	public List<Area> findAll();

	public List<Area> findByCitycodeLikeOrShortcodeLike(String string, String string2);

}
