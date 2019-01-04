/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shopuser.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.shopuser.entity.ShopUser;
import com.thinkgem.jeesite.modules.shopuser.dao.ShopUserDao;

/**
 * 会员管理Service
 * @author zhb
 * @version 2018-12-01
 */
@Service
@Transactional(readOnly = true)
public class ShopUserService extends CrudService<ShopUserDao, ShopUser> {

	@Autowired
	private ShopUserDao shopUserDao;
	public ShopUser get(String id) {
		return super.get(id);
	}
	
	public List<ShopUser> findList(ShopUser shopUser) {
		return super.findList(shopUser);
	}
	
	public Page<ShopUser> findPage(Page<ShopUser> page, ShopUser shopUser) {
		return super.findPage(page, shopUser);
	}
	
	@Transactional(readOnly = false)
	public void save(ShopUser shopUser) {
		super.save(shopUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopUser shopUser) {
		super.delete(shopUser);
	}
	@Transactional(readOnly = false)
	public Integer islock(ShopUser shopUser) {
		
		Integer islock = shopUserDao.islock(shopUser);
		return islock;
	}
	
}