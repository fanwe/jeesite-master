/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shopwarehouse.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.shopwarehouse.entity.ShopWarehose;
import com.thinkgem.jeesite.modules.shopwarehouse.dao.ShopWarehoseDao;

/**
 * 仓库列表Service
 * @author zhb
 * @version 2018-11-29
 */
@Service
@Transactional(readOnly = true)
public class ShopWarehoseService extends CrudService<ShopWarehoseDao, ShopWarehose> {

	public ShopWarehose get(String id) {
		return super.get(id);
	}
	
	public List<ShopWarehose> findList(ShopWarehose shopWarehose) {
		return super.findList(shopWarehose);
	}
	
	public Page<ShopWarehose> findPage(Page<ShopWarehose> page, ShopWarehose shopWarehose) {
		return super.findPage(page, shopWarehose);
	}
	
	@Transactional(readOnly = false)
	public void save(ShopWarehose shopWarehose) {
		super.save(shopWarehose);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopWarehose shopWarehose) {
		super.delete(shopWarehose);
	}
	
}