package cn.itcast.bos.service.system;

import java.util.List;

import cn.itcast.bos.domain.system.User;

public interface UserService {

	User findByUsernameAndPassword(String username, String password);

	public List<User> findAll();

	void save(User model, String roleIds);

}
