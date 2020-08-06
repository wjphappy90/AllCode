package cn.itcast.bos.action.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;

import cn.itcast.bos.domain.base.Courier;
import cn.itcast.bos.service.base.CourierService;
@Controller
@Scope("prototype")
@ParentPackage("struts-default")
@Namespace("/")
public class CourierAction extends BaseAction<Courier> {

	@Autowired
	private CourierService service;
	
	
	@Action("courierAction_findByPage")
	public void findByPage(){
//		DetachedCriteria dc  = DetachedCriteria.forClass(Courier.class);
//		if(StringUtils.isNotEmpty(model.getCourierNum())){
//		 dc.add(Restrictions.like("courierNum", "%"+model.getCourierNum()+"%"));
//		}
		
//		DetachedCriteria=Specification      Restrictions=CriteriaBuilder  root:查询的入口 查询的主体
		
		Specification specification = new Specification<Courier>() {
			@Override
			public Predicate toPredicate(Root<Courier> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
//				Root<Courier> root  查询的主体对象Courier对象 , CriteriaQuery<?> query 查询组合对象, CriteriaBuilder cb 查询组合对象
//				按照工号
				List<Predicate> list = new ArrayList<Predicate>();
				if(StringUtils.isNotBlank(model.getCourierNum()) ){
				   Predicate predicate1 = cb.like(root.get("courierNum").as(String.class), "%"+model.getCourierNum()+"%");
				   list.add(predicate1);
				}
//				收派标准的名称
				if(model.getStandard()!=null&&StringUtils.isNotBlank(model.getStandard().getName())){
					Join<Object, Object> join = root.join("standard");  //相当于standard对象
					Predicate predicate2 = cb.like(join.get("name").as(String.class), "%"+model.getStandard().getName()+"%");
					list.add(predicate2);
				}
//				公司名称
				if(StringUtils.isNotBlank(model.getCompany()) ){
					   Predicate predicate3 = cb.like(root.get("company").as(String.class), "%"+model.getCompany()+"%");
					   list.add(predicate3);
					}
//				类型
				if(StringUtils.isNotBlank(model.getType()) ){
					   Predicate predicate4 = cb.like(root.get("type").as(String.class), "%"+model.getType()+"%");
					   list.add(predicate4);
				}
				
				if(list.size()==0){
					return null;
				}
//				Predicate数组
				Predicate[] predicates = new Predicate[list.size()];
				predicates = list.toArray(predicates);
				return cb.and(predicates);
			}
		};
		
		Pageable pageable = new PageRequest(page-1, rows);
		Page<Courier> page = service.findByPage(specification , pageable);	
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("total", page.getTotalElements());
		map.put("rows", page.getContent());
		javaToJson(map,new String[]{"fixedAreas"});
	}


	private String ids; // "23,24"
	public void setIds(String ids) {
		this.ids = ids;
	}
	@Action("courierAction_deleteBatch")
	public void deleteBatch(){
//		data的数据格式 {"success":true|false,"message":"保存成功|保存失败"}
		try {
			service.deleteBatch(ids);
//			成功
			javaToJson(ajaxReturn(true, "操作成功"),null);
		} catch (Exception e) {
//			失败
			javaToJson(ajaxReturn(false, "操作失败"),null);
			e.printStackTrace();
		}
	}



	@Action("courierAction_save")
	public void save(){
//		data的数据格式 {"success":true|false,"message":"保存成功|保存失败"}
		try {
			service.save(model);
//			成功
			javaToJson(ajaxReturn(true, "操作成功"),null);
		} catch (Exception e) {
//			失败
			javaToJson(ajaxReturn(false, "操作失败"),null);
			e.printStackTrace();
		}
	}
	
	@Action("courierAction_findAll")
	public void findAll(){
		List<Courier> list = service.findAll();
		javaToJson(list, new String[]{"fixedAreas"});
		
	}

}
