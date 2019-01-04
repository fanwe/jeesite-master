/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.userlevel.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.userlevel.entity.ShopUserlevel;

/**
 * 会员等级DAO接口
 * @author zhb
 * @version 2018-12-01
 */
@MyBatisDao
public interface ShopUserlevelDao extends CrudDao<ShopUserlevel> {
	
}