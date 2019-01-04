/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shopwarehouse.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 仓库列表Entity
 * @author zhb
 * @version 2018-11-29
 */
public class ShopWarehose extends DataEntity<ShopWarehose> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 仓库名称
	private String city;		// 仓库所在城市
	private String address;		// 仓库地址
	private String description;		// 描述
	public String getCityname() {
		return cityname;
	}

	public void setCityname(String cityname) {
		this.cityname = cityname;
	}

	private String cityname;
	
	public ShopWarehose() {
		super();
	}

	public ShopWarehose(String id){
		super(id);
	}

	@Length(min=0, max=255, message="仓库名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="仓库所在城市长度必须介于 0 和 255 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=0, max=255, message="仓库地址长度必须介于 0 和 255 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=0, max=100, message="描述长度必须介于 0 和 100 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}