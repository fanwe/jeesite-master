/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shareholdersinformation.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 公司持股人情况Entity
 * @author zhb
 * @version 2018-12-10
 */
public class ShareholdersInformation extends DataEntity<ShareholdersInformation> {
	
	private static final long serialVersionUID = 1L;
	private String num;		// 序号
	private String shareholderName;		// 股东姓名
	private String sharesNumStart;		// 期初持股数
	private String ownershipChange;		// 持股变动
	private String sharesNumEnd;		// 期末持股数
	private String shareholdingRatio;		// 期末持股比例
	private String sharesNumLimitEnd;		// 期末持有限售股份数量
	private String sharesNumInfinteEnd;		// 期末持无限售股份数量
	private String companyIntroductionId;		// 对应公司的id
	
	public ShareholdersInformation() {
		super();
	}

	public ShareholdersInformation(String id){
		super(id);
	}

	@Length(min=0, max=11, message="序号长度必须介于 0 和 11 之间")
	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}
	
	@Length(min=0, max=255, message="股东姓名长度必须介于 0 和 255 之间")
	public String getShareholderName() {
		return shareholderName;
	}

	public void setShareholderName(String shareholderName) {
		this.shareholderName = shareholderName;
	}
	
	@Length(min=0, max=255, message="期初持股数长度必须介于 0 和 255 之间")
	public String getSharesNumStart() {
		return sharesNumStart;
	}

	public void setSharesNumStart(String sharesNumStart) {
		this.sharesNumStart = sharesNumStart;
	}
	
	@Length(min=0, max=255, message="持股变动长度必须介于 0 和 255 之间")
	public String getOwnershipChange() {
		return ownershipChange;
	}

	public void setOwnershipChange(String ownershipChange) {
		this.ownershipChange = ownershipChange;
	}
	
	@Length(min=0, max=255, message="期末持股数长度必须介于 0 和 255 之间")
	public String getSharesNumEnd() {
		return sharesNumEnd;
	}

	public void setSharesNumEnd(String sharesNumEnd) {
		this.sharesNumEnd = sharesNumEnd;
	}
	
	@Length(min=0, max=255, message="期末持股比例长度必须介于 0 和 255 之间")
	public String getShareholdingRatio() {
		return shareholdingRatio;
	}

	public void setShareholdingRatio(String shareholdingRatio) {
		this.shareholdingRatio = shareholdingRatio;
	}
	
	@Length(min=0, max=255, message="期末持有限售股份数量长度必须介于 0 和 255 之间")
	public String getSharesNumLimitEnd() {
		return sharesNumLimitEnd;
	}

	public void setSharesNumLimitEnd(String sharesNumLimitEnd) {
		this.sharesNumLimitEnd = sharesNumLimitEnd;
	}
	
	@Length(min=0, max=255, message="期末持无限售股份数量长度必须介于 0 和 255 之间")
	public String getSharesNumInfinteEnd() {
		return sharesNumInfinteEnd;
	}

	public void setSharesNumInfinteEnd(String sharesNumInfinteEnd) {
		this.sharesNumInfinteEnd = sharesNumInfinteEnd;
	}
	
	@Length(min=0, max=255, message="对应公司的id长度必须介于 0 和 255 之间")
	public String getCompanyIntroductionId() {
		return companyIntroductionId;
	}

	public void setCompanyIntroductionId(String companyIntroductionId) {
		this.companyIntroductionId = companyIntroductionId;
	}
	
}