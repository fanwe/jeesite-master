/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.error.service.kuaijishishiwusuo;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.error.entity.kuaijishishiwusuo.Errorfile;
import com.thinkgem.jeesite.modules.error.dao.kuaijishishiwusuo.ErrorfileDao;

/**
 * 公司聘用的会计师事务所(未匹配到的)Service
 * @author zhb
 * @version 2018-12-11
 */
@Service
@Transactional(readOnly = true)
public class ErrorfileService extends CrudService<ErrorfileDao, Errorfile> {

	public Errorfile get(String id) {
		return super.get(id);
	}
	public Errorfile getFileName(String fileName) {
		return super.getFileName(fileName);
	}
	
	public List<Errorfile> findList(Errorfile errorfile) {
		return super.findList(errorfile);
	}
	
	public Page<Errorfile> findPage(Page<Errorfile> page, Errorfile errorfile) {
		return super.findPage(page, errorfile);
	}
	
	@Transactional(readOnly = false)
	public void save(Errorfile errorfile) {
		super.save(errorfile);
	}
	
	@Transactional(readOnly = false)
	public void delete(Errorfile errorfile) {
		super.delete(errorfile);
	}
	
}