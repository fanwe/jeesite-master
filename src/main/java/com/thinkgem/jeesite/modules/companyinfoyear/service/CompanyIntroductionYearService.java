/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.companyinfoyear.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.companyinfoyear.entity.CompanyIntroductionYear;
import com.thinkgem.jeesite.modules.companyinfoyear.dao.CompanyIntroductionYearDao;

/**
 * 公司年报Service
 * @author zhb
 * @version 2018-12-16
 */
@Service
@Transactional(readOnly = true)
public class CompanyIntroductionYearService extends CrudService<CompanyIntroductionYearDao, CompanyIntroductionYear> {
	@Autowired
	private CompanyIntroductionYearDao companyIntroductionYearDao;
	public CompanyIntroductionYear get(String id) {
		return super.get(id);
	}
	
	public List<CompanyIntroductionYear> findList(CompanyIntroductionYear companyIntroductionYear) {
		return super.findList(companyIntroductionYear);
	}
	
	public Page<CompanyIntroductionYear> findPage(Page<CompanyIntroductionYear> page, CompanyIntroductionYear companyIntroductionYear) {
		return super.findPage(page, companyIntroductionYear);
	}
	
	@Transactional(readOnly = false)
	public void save(CompanyIntroductionYear companyIntroductionYear) {
		super.save(companyIntroductionYear);
	}
	
	@Transactional(readOnly = false)
	public void delete(CompanyIntroductionYear companyIntroductionYear) {
		super.delete(companyIntroductionYear);
	}
	@Transactional(readOnly = false)
	public int findByRecordCount(String pdfFile) {
		return companyIntroductionYearDao.findByRecordCount(pdfFile);
		
	}
	
}