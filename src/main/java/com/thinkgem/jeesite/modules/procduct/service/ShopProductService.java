/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.procduct.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.procduct.entity.ShopProduct;
import com.thinkgem.jeesite.modules.procduct.dao.ShopProductDao;

/**
 * 商品管理Service
 * @author zhb
 * @version 2018-11-30
 */
@Service
@Transactional(readOnly = true)
public class ShopProductService extends CrudService<ShopProductDao, ShopProduct> {

	public ShopProduct get(String id) {
		return super.get(id);
	}
	
	public List<ShopProduct> findList(ShopProduct shopProduct) {
		return super.findList(shopProduct);
	}
	
	public Page<ShopProduct> findPage(Page<ShopProduct> page, ShopProduct shopProduct) {
		return super.findPage(page, shopProduct);
	}
	
	@Transactional(readOnly = false)
	public void save(ShopProduct shopProduct) {
		super.save(shopProduct);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopProduct shopProduct) {
		super.delete(shopProduct);
	}
	
}