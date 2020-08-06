package cn.itcast.bos.service.base.impl;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.CourierDao;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.base.CourierService;
@Service
@Transactional
public class CourierServiceImpl implements CourierService {
	@Autowired
	private CourierDao dao;

	@Override
	public void save(Courier model) {
		dao.save(model);
	}

	@Override
	public Page<Courier> findByPage(Pageable pageable) {
		return dao.findAll(pageable);
	}

//	@RequiresPermissions("courier-delete")
	public void deleteBatch(String string) {
		Subject subject = SecurityUtils.getSubject();
		if(!subject.isPermitted("courier-delete")){
			throw new UnauthorizedException();
		}
//		string "23,24"
		String[] ids = string.split(",");
		for (int i = 0; i < ids.length; i++) {
			dao.updateDeltag(Integer.parseInt(ids[i]));
		}
	}

	@Override
	public Page<Courier> findByPage(Specification specification, Pageable pageable) {
		return dao.findAll(specification, pageable);
	}

	@Override
	public List<Courier> findAll() {
		return dao.findAll();
	}
}
