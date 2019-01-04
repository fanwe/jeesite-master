/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.userlevel.entity;

import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotNull;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 会员等级Entity
 * @author zhb
 * @version 2018-12-01
 */
public class ShopUserlevel extends DataEntity<ShopUserlevel> {
	
	private static final long serialVersionUID = 1L;
	private String code;		// 会员编码
	private String name;		// 会员名称
	private Integer minscore;		// 最小积分
	private Integer maxscore;		// 最大积分
	private String description;		// 描述
	
	public ShopUserlevel() {
		super();
	}

	public ShopUserlevel(String id){
		super(id);
	}

	@Length(min=1, max=255, message="会员编码长度必须介于 1 和 255 之间")
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	@Length(min=1, max=255, message="会员名称长度必须介于 1 和 255 之间")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@NotNull(message="最小积分不能为空")
	public Integer getMinscore() {
		return minscore;
	}

	public void setMinscore(Integer minscore) {
		this.minscore = minscore;
	}
	
	@NotNull(message="最大积分不能为空")
	public Integer getMaxscore() {
		return maxscore;
	}

	public void setMaxscore(Integer maxscore) {
		this.maxscore = maxscore;
	}
	
	@Length(min=0, max=100, message="描述长度必须介于 0 和 100 之间")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}