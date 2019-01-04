/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.userlevel.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.userlevel.entity.ShopUserlevel;
import com.thinkgem.jeesite.modules.userlevel.dao.ShopUserlevelDao;

/**
 * 会员等级Service
 * @author zhb
 * @version 2018-12-01
 */
@Service
@Transactional(readOnly = true)
public class ShopUserlevelService extends CrudService<ShopUserlevelDao, ShopUserlevel> {

	public ShopUserlevel get(String id) {
		return super.get(id);
	}
	
	public List<ShopUserlevel> findList(ShopUserlevel shopUserlevel) {
		return super.findList(shopUserlevel);
	}
	
	public Page<ShopUserlevel> findPage(Page<ShopUserlevel> page, ShopUserlevel shopUserlevel) {
		return super.findPage(page, shopUserlevel);
	}
	
	@Transactional(readOnly = false)
	public void save(ShopUserlevel shopUserlevel) {
		super.save(shopUserlevel);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopUserlevel shopUserlevel) {
		super.delete(shopUserlevel);
	}
	
}