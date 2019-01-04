/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.error.dao.kuaijishishiwusuo;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.error.entity.kuaijishishiwusuo.Errorfile;

/**
 * 公司聘用的会计师事务所(未匹配到的)DAO接口
 * @author zhb
 * @version 2018-12-11
 */
@MyBatisDao
public interface ErrorfileDao extends CrudDao<Errorfile> {
	
}