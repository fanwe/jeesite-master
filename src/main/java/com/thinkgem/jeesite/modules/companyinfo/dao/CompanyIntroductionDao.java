/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.companyinfo.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.companyinfo.entity.CompanyIntroduction;

/**
 * 公司情况DAO接口
 * @author zhb
 * @version 2018-12-08
 */
@MyBatisDao
public interface CompanyIntroductionDao extends CrudDao<CompanyIntroduction> {
	
}