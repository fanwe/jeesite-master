/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.classes.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.classes.entity.DemoClasses;
import com.thinkgem.jeesite.modules.classes.dao.DemoClassesDao;

/**
 * 班级信息Service
 * @author zhb
 * @version 2018-11-28
 */
@Service
@Transactional(readOnly = true)
public class DemoClassesService extends CrudService<DemoClassesDao, DemoClasses> {

	public DemoClasses get(String id) {
		return super.get(id);
	}
	
	public List<DemoClasses> findList(DemoClasses demoClasses) {
		return super.findList(demoClasses);
	}
	
	public Page<DemoClasses> findPage(Page<DemoClasses> page, DemoClasses demoClasses) {
		return super.findPage(page, demoClasses);
	}
	
	@Transactional(readOnly = false)
	public void save(DemoClasses demoClasses) {
		super.save(demoClasses);
	}
	
	@Transactional(readOnly = false)
	public void delete(DemoClasses demoClasses) {
		super.delete(demoClasses);
	}
	
}