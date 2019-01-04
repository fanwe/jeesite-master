/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shopbrand.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.shopbrand.entity.ShopBrand;
import com.thinkgem.jeesite.modules.shopbrand.dao.ShopBrandDao;

/**
 * 商品品牌Service
 * @author zhb
 * @version 2018-11-29
 */
@Service
@Transactional(readOnly = true)
public class ShopBrandService extends CrudService<ShopBrandDao, ShopBrand> {

	public ShopBrand get(String id) {
		return super.get(id);
	}
	
	public List<ShopBrand> findList(ShopBrand shopBrand) {
		return super.findList(shopBrand);
	}
	
	public Page<ShopBrand> findPage(Page<ShopBrand> page, ShopBrand shopBrand) {
		return super.findPage(page, shopBrand);
	}
	
	@Transactional(readOnly = false)
	public void save(ShopBrand shopBrand) {
		super.save(shopBrand);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopBrand shopBrand) {
		super.delete(shopBrand);
	}
	
}