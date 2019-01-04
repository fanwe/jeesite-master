/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 订单管理Entity
 * @author zhb
 * @version 2018-12-02
 */
public class ShopOrder extends DataEntity<ShopOrder> {
	
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	private static final long serialVersionUID = 1L;
	private String orderno;		// 订单号
	private String userid;		// 会员昵称
	private Integer paytype;		// 支付方式
	private Integer paystatus;	// 支付状态
	private Integer orderstatus;	//订单状态	
	private Double amcount;		// 订单总额
	private Double fee;		// 订单运费
	private String quantity;		// 商品总数量
	private String city;		// 送货地址
	private String address;		// 送货详细地址
	private String username;
	private String cityName;
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getPaystatus() {
		return paystatus;
	}

	public void setPaystatus(Integer paystatus) {
		this.paystatus = paystatus;
	}

	private String expressno;		// 快递运单号
	private String expresscompanyname;		// 快递公司
	private String description;		// 描述
	
	public ShopOrder() {
		super();
	}

	public ShopOrder(String id){
		super(id);
	}

	@Length(min=1, max=255, message="订单号长度必须介于 1 和 255 之间")
	public String getOrderno() {
		return orderno;
	}

	public void setOrderno(String orderno) {
		this.orderno = orderno;
	}
	
	@Length(min=1, max=255, message="会员昵称长度必须介于 1 和 255 之间")
	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	@NotNull(message="支付方式不能为空")
	public Integer getPaytype() {
		return paytype;
	}

	public void setPaytype(Integer paytype) {
		this.paytype = paytype;
	}
	
	@NotNull(message="支付状态不能为空")
	public Integer getOrderstatus() {
		return orderstatus;
	}

	public void setOrderstatus(Integer orderstatus) {
		this.orderstatus = orderstatus;
	}
	
	@NotNull(message="订单总额不能为空")
	public Double getAmcount() {
		return amcount;
	}

	public void setAmcount(Double amcount) {
		this.amcount = amcount;
	}
	
	@NotNull(message="订单运费不能为空")
	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}
	
	@Length(min=1, max=255, message="商品总数量长度必须介于 1 和 255 之间")
	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	
	@Length(min=1, max=255, message="送货地址长度必须介于 1 和 255 之间")
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
	
	@Length(min=1, max=255, message="送货详细地址长度必须介于 1 和 255 之间")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
	@Length(min=1, max=255, message="快递运单号长度必须介于 1 和 255 之间")
	public String getExpressno() {
		return expressno;
	}

	public void setExpressno(String expressno) {
		this.expressno = expressno;
	}
	
	@Length(min=1, max=255, message="快递公司长度必须介于 1 和 255 之间")
	public String getExpresscompanyname() {
		return expresscompanyname;
	}

	public void setExpresscompanyname(String expresscompanyname) {
		this.expresscompanyname = expresscompanyname;
	}
	
	@Length(min=1, max=100, message="描述长度必须介于 1 和 100 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}