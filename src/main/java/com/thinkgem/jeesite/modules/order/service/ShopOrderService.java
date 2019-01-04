/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.service.CrudService;
import com.thinkgem.jeesite.modules.order.entity.ShopOrder;
import com.thinkgem.jeesite.modules.order.dao.ShopOrderDao;

/**
 * 订单管理Service
 * @author zhb
 * @version 2018-12-02
 */
@Service
@Transactional(readOnly = true)
public class ShopOrderService extends CrudService<ShopOrderDao, ShopOrder> {

	public ShopOrder get(String id) {
		return super.get(id);
	}
	
	public List<ShopOrder> findList(ShopOrder shopOrder) {
		return super.findList(shopOrder);
	}
	
	public Page<ShopOrder> findPage(Page<ShopOrder> page, ShopOrder shopOrder) {
		return super.findPage(page, shopOrder);
	}
	
	@Transactional(readOnly = false)
	public void save(ShopOrder shopOrder) {
		super.save(shopOrder);
	}
	
	@Transactional(readOnly = false)
	public void delete(ShopOrder shopOrder) {
		super.delete(shopOrder);
	}
	
}