/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.procduct.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 商品管理Entity
 * @author zhb
 * @version 2018-11-30
 */
public class ShopProduct extends DataEntity<ShopProduct> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 商品名称
	private String subtitle;		// 商品副标题
	private String category;		// 商品类型
	private String mainImg;		// 商品图片
	private String detail;		// 商品详情
	private Double price;		// 商品价格
	private Integer stock;		// 商品库存
	private Integer status;		// 商品状态
	private String brand;		// 商品品牌
	private String warehouse;		// 所在仓库
	private String description;		// 描述
	private String categoryname;
	private String warehouseName;
	private String brandName;
	
	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getCategoryname() {
		return categoryname;
	}

	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public ShopProduct() {
		super();
	}

	public ShopProduct(String id){
		super(id);
	}

	@Length(min=0, max=255, message="商品名称长度必须介于 0 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=255, message="商品副标题长度必须介于 0 和 255 之间")
	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}
	
	@Length(min=0, max=255, message="商品类型长度必须介于 0 和 255 之间")
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
	
	@Length(min=0, max=255, message="商品图片长度必须介于 0 和 255 之间")
	public String getMainImg() {
		return mainImg;
	}

	public void setMainImg(String mainImg) {
		this.mainImg = mainImg;
	}
	
	@Length(min=0, max=1000, message="商品详情长度必须介于 0 和 1000 之间")
	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	@Length(min=0, max=255, message="商品品牌长度必须介于 0 和 255 之间")
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
	
	@Length(min=0, max=255, message="所在仓库长度必须介于 0 和 255 之间")
	public String getWarehouse() {
		return warehouse;
	}

	public void setWarehouse(String warehouse) {
		this.warehouse = warehouse;
	}
	
	@Length(min=0, max=100, message="描述长度必须介于 0 和 100 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}