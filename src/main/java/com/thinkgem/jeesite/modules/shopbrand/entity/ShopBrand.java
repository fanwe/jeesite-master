/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shopbrand.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 商品品牌Entity
 * @author zhb
 * @version 2018-11-29
 */
public class ShopBrand extends DataEntity<ShopBrand> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String brandUrl;		// 品牌图片路径
	private String description;		// 描述
	
	public ShopBrand() {
		super();
	}

	public ShopBrand(String id){
		super(id);
	}

	@Length(min=0, max=255, message="名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="品牌图片路径长度必须介于 0 和 255 之间")
	public String getBrandUrl() {
		return brandUrl;
	}

	public void setBrandUrl(String brandUrl) {
		this.brandUrl = brandUrl;
	}
	
	@Length(min=0, max=100, message="描述长度必须介于 0 和 100 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}