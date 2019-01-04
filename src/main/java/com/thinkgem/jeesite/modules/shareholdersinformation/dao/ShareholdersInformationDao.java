/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shareholdersinformation.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.shareholdersinformation.entity.ShareholdersInformation;

/**
 * 公司持股人情况DAO接口
 * @author zhb
 * @version 2018-12-10
 */
@MyBatisDao
public interface ShareholdersInformationDao extends CrudDao<ShareholdersInformation> {
	
}