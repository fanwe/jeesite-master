/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shopactive.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.shopactive.entity.ShopPromotionActivity;

/**
 * 促销活动管理DAO接口
 * @author zhb
 * @version 2018-12-02
 */
@MyBatisDao
public interface ShopPromotionActivityDao extends CrudDao<ShopPromotionActivity> {
	
}