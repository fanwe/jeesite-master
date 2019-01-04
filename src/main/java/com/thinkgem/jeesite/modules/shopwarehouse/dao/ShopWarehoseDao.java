/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shopwarehouse.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.shopwarehouse.entity.ShopWarehose;

/**
 * 仓库列表DAO接口
 * @author zhb
 * @version 2018-11-29
 */
@MyBatisDao
public interface ShopWarehoseDao extends CrudDao<ShopWarehose> {
	
}