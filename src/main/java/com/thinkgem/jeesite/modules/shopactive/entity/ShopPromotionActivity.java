/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shopactive.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 促销活动管理Entity
 * @author zhb
 * @version 2018-12-02
 */
public class ShopPromotionActivity extends DataEntity<ShopPromotionActivity> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 活动名称
	private String mainimg;		// 活动主图
	private String content;		// 活动内容
	private String description;		// 描述
	
	public ShopPromotionActivity() {
		super();
	}

	public ShopPromotionActivity(String id){
		super(id);
	}

	@Length(min=1, max=255, message="活动名称长度必须介于 1 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=1, max=255, message="活动主图长度必须介于 1 和 255 之间")
	public String getMainimg() {
		return mainimg;
	}

	public void setMainimg(String mainimg) {
		this.mainimg = mainimg;
	}
	
	@Length(min=1, max=255, message="活动内容长度必须介于 1 和 255 之间")
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@Length(min=0, max=100, message="描述长度必须介于 0 和 100 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}