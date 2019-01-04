/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shopcatgory.dao;

import com.thinkgem.jeesite.common.persistence.TreeDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.shopcatgory.entity.ShopCategory;

/**
 * 商品分类DAO接口
 * @author zhb
 * @version 2018-11-29
 */
@MyBatisDao
public interface ShopCategoryDao extends TreeDao<ShopCategory> {
	
}