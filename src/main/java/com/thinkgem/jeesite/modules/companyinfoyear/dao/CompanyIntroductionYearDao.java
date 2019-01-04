/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.companyinfoyear.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.companyinfoyear.entity.CompanyIntroductionYear;

/**
 * 公司年报DAO接口
 * @author zhb
 * @version 2018-12-16
 */
@MyBatisDao
public interface CompanyIntroductionYearDao extends CrudDao<CompanyIntroductionYear> {

	Integer findByRecordCount(String pdfFile);
	
}