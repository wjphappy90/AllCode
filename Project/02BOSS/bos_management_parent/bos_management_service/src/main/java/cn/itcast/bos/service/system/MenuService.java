package cn.itcast.bos.service.system;

import java.util.List;

import cn.itcast.bos.domain.system.Menu;
import cn.itcast.bos.domain.system.User;

public interface MenuService {

	public List<Menu> findAll();

	public List<Menu> findByParentMenuIsNull();

	public void save(Menu model);

	public List<Menu> findByUser(User user);

}
