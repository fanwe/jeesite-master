/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.student.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.student.entity.DemoStudent;
import com.thinkgem.jeesite.modules.student.dao.DemoStudentDao;

/**
 * 学生表Service
 * @author zhb
 * @version 2018-11-28
 */
@Service
@Transactional(readOnly = true)
public class DemoStudentService extends CrudService<DemoStudentDao, DemoStudent> {

	public DemoStudent get(String id) {
		return super.get(id);
	}
	
	public List<DemoStudent> findList(DemoStudent demoStudent) {
		return super.findList(demoStudent);
	}
	
	public Page<DemoStudent> findPage(Page<DemoStudent> page, DemoStudent demoStudent) {
		return super.findPage(page, demoStudent);
	}
	
	@Transactional(readOnly = false)
	public void save(DemoStudent demoStudent) {
		super.save(demoStudent);
	}
	
	@Transactional(readOnly = false)
	public void delete(DemoStudent demoStudent) {
		super.delete(demoStudent);
	}
	
}