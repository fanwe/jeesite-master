/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shopactive.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.shopactive.entity.ShopPromotionActivity;
import com.thinkgem.jeesite.modules.shopactive.dao.ShopPromotionActivityDao;

/**
 * 促销活动管理Service
 * @author zhb
 * @version 2018-12-02
 */
@Service
@Transactional(readOnly = true)
public class ShopPromotionActivityService extends CrudService<ShopPromotionActivityDao, ShopPromotionActivity> {

	public ShopPromotionActivity get(String id) {
		return super.get(id);
	}
	
	public List<ShopPromotionActivity> findList(ShopPromotionActivity shopPromotionActivity) {
		return super.findList(shopPromotionActivity);
	}
	
	public Page<ShopPromotionActivity> findPage(Page<ShopPromotionActivity> page, ShopPromotionActivity shopPromotionActivity) {
		return super.findPage(page, shopPromotionActivity);
	}
	
	@Transactional(readOnly = false)
	public void save(ShopPromotionActivity shopPromotionActivity) {
		super.save(shopPromotionActivity);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopPromotionActivity shopPromotionActivity) {
		super.delete(shopPromotionActivity);
	}
	
}