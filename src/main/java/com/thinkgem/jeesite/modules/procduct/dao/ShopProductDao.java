/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.procduct.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.procduct.entity.ShopProduct;

/**
 * 商品管理DAO接口
 * @author zhb
 * @version 2018-11-30
 */
@MyBatisDao
public interface ShopProductDao extends CrudDao<ShopProduct> {
	
}