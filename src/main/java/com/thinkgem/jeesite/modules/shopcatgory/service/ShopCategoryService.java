/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shopcatgory.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.service.TreeService;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.shopcatgory.entity.ShopCategory;
import com.thinkgem.jeesite.modules.shopcatgory.dao.ShopCategoryDao;

/**
 * 商品分类Service
 * @author zhb
 * @version 2018-11-29
 */
@Service
@Transactional(readOnly = true)
public class ShopCategoryService extends TreeService<ShopCategoryDao, ShopCategory> {

	public ShopCategory get(String id) {
		return super.get(id);
	}
	
	public List<ShopCategory> findList(ShopCategory shopCategory) {
		if (StringUtils.isNotBlank(shopCategory.getParentIds())){
			shopCategory.setParentIds(","+shopCategory.getParentIds()+",");
		}
		return super.findList(shopCategory);
	}
	
	@Transactional(readOnly = false)
	public void save(ShopCategory shopCategory) {
		super.save(shopCategory);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopCategory shopCategory) {
		super.delete(shopCategory);
	}
	
}