/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.order.entity.ShopOrder;

/**
 * 订单管理DAO接口
 * @author zhb
 * @version 2018-12-02
 */
@MyBatisDao
public interface ShopOrderDao extends CrudDao<ShopOrder> {
	
}