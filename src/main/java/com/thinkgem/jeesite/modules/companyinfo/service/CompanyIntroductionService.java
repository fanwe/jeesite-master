/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.companyinfo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.companyinfo.entity.CompanyIntroduction;
import com.thinkgem.jeesite.modules.companyinfo.dao.CompanyIntroductionDao;

/**
 * 公司情况Service
 * @author zhb
 * @version 2018-12-08
 */
@Service
@Transactional(readOnly = true)
public class CompanyIntroductionService extends CrudService<CompanyIntroductionDao, CompanyIntroduction> {

	public CompanyIntroduction get(String id) {
		return super.get(id);
	}
	@Transactional(readOnly=true)
	public CompanyIntroduction getFileName(String fileName) {
		return super.getFileName(fileName);
	}
	public List<CompanyIntroduction> findList(CompanyIntroduction companyIntroduction) {
		return super.findList(companyIntroduction);
	}
	
	public Page<CompanyIntroduction> findPage(Page<CompanyIntroduction> page, CompanyIntroduction companyIntroduction) {
		return super.findPage(page, companyIntroduction);
	}
	
	@Transactional(readOnly = false)
	public void save(CompanyIntroduction companyIntroduction) {
		super.save(companyIntroduction);
	}
	
	@Transactional(readOnly = false)
	public void delete(CompanyIntroduction companyIntroduction) {
		super.delete(companyIntroduction);
	}
	
}