package cn.itcast.bos.service.base.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.bos.dao.base.CourierDao;
import cn.itcast.bos.dao.base.FixedAreaDao;
import cn.itcast.bos.dao.base.SubAreaDao;
import cn.itcast.bos.dao.base.TakeTimeDao;
import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.domain.base.FixedArea;
import cn.itcast.bos.domain.base.SubArea;
import cn.itcast.bos.domain.base.TakeTime;
import cn.itcast.bos.service.base.FixedAreaService;

@Service
@Transactional
public class FixedAreaServiceImpl implements FixedAreaService {
	
	@Autowired
	private FixedAreaDao dao;
	@Autowired
	private SubAreaDao sdao;
	@Autowired
	private CourierDao cdao;
	@Autowired
	private TakeTimeDao tdao;

	@Override
	public void save(FixedArea model) {
		dao.save(model);
	}

	@Override
	public Page<FixedArea> findByPage(Pageable pageable) {
		return dao.findAll(pageable);
	}

	@Override
	public void save(FixedArea model, String ids) {
//		1、保存一个定区
		model = dao.save(model);
//		2、把每个分区中设置定区的id
		String[] subAreaIds = ids.split(",");
		for (String id : subAreaIds) {
			SubArea subArea = sdao.findOne(id);
			subArea.setFixedArea(model);
		}
		
	}

	@Override
	public void associationCourierToFixedArea(String id, Integer courierId, Integer takeTimeId) {
//		1、在定区和快递员之间的中间表添加一条数据
		FixedArea fixedArea = dao.findOne(id);
		Courier courier = cdao.findOne(courierId);
		fixedArea.getCouriers().add(courier);
//		2、修改快递员的taketime
		TakeTime takeTime  = tdao.findOne(takeTimeId);
		courier.setTakeTime(takeTime);
	}

}
