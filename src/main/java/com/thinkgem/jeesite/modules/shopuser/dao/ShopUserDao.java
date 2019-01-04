/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shopuser.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.shopuser.entity.ShopUser;

/**
 * 会员管理DAO接口
 * @author zhb
 * @version 2018-12-01
 */
@MyBatisDao
public interface ShopUserDao extends CrudDao<ShopUser> {

	Integer islock(ShopUser shopUser);
	
}