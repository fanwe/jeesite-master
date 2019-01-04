/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.companyinfoyear.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 公司年报Entity
 * @author zhb
 * @version 2018-12-16
 */
public class CompanyIntroductionYear extends DataEntity<CompanyIntroductionYear> {
	
	private static final long serialVersionUID = 1L;
	private String fileName;		// 文件名称
	private String title;		// 文件标题
	private String chineseNameOfCompany;		// 公司中文法定名称
	private String englishNameOfCompany;		// 公司法定英文名称
	private String manageScopeCompany;		// 经营范围
	private String accountingFirm;		// 会计师事务所
	private String chineseCpa1;		// 中国注册会计师1
	private String chineseCpa2;		// 中国注册会计师2
	private String accountingDirector;		// 公司会计负责人
	private String organizationAccountingDirector;		// 公司会计机构负责人
	private String directorSupervisorTopmanagerIntro;		// 公司董事、监事、高管简介
	private String leadUnderwriter1;		// 主承销商1
	private String leadUnderwriter2;		// 主承销商2
	private String creditRatingAgencies;		// 资信评级机构
	private String releaseTime;		// 发布时间
	private String charge;		// 收费
	private String engageDismissAccountingFirm;		// 聘用、解聘会计师事务所情况
	
	public CompanyIntroductionYear() {
		super();
	}

	public CompanyIntroductionYear(String id){
		super(id);
	}

	@Length(min=0, max=255, message="文件名称长度必须介于 0 和 255 之间")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	@Length(min=0, max=255, message="文件标题长度必须介于 0 和 255 之间")
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@Length(min=0, max=255, message="公司中文法定名称长度必须介于 0 和 255 之间")
	public String getChineseNameOfCompany() {
		return chineseNameOfCompany;
	}

	public void setChineseNameOfCompany(String chineseNameOfCompany) {
		this.chineseNameOfCompany = chineseNameOfCompany;
	}
	
	@Length(min=0, max=255, message="公司法定英文名称长度必须介于 0 和 255 之间")
	public String getEnglishNameOfCompany() {
		return englishNameOfCompany;
	}

	public void setEnglishNameOfCompany(String englishNameOfCompany) {
		this.englishNameOfCompany = englishNameOfCompany;
	}
	
	@Length(min=0, max=255, message="经营范围长度必须介于 0 和 255 之间")
	public String getManageScopeCompany() {
		return manageScopeCompany;
	}

	public void setManageScopeCompany(String manageScopeCompany) {
		this.manageScopeCompany = manageScopeCompany;
	}
	
	@Length(min=0, max=255, message="会计师事务所长度必须介于 0 和 255 之间")
	public String getAccountingFirm() {
		return accountingFirm;
	}

	public void setAccountingFirm(String accountingFirm) {
		this.accountingFirm = accountingFirm;
	}
	
	@Length(min=0, max=255, message="中国注册会计师1长度必须介于 0 和 255 之间")
	public String getChineseCpa1() {
		return chineseCpa1;
	}

	public void setChineseCpa1(String chineseCpa1) {
		this.chineseCpa1 = chineseCpa1;
	}
	
	@Length(min=0, max=255, message="中国注册会计师2长度必须介于 0 和 255 之间")
	public String getChineseCpa2() {
		return chineseCpa2;
	}

	public void setChineseCpa2(String chineseCpa2) {
		this.chineseCpa2 = chineseCpa2;
	}
	
	@Length(min=0, max=255, message="公司会计负责人长度必须介于 0 和 255 之间")
	public String getAccountingDirector() {
		return accountingDirector;
	}

	public void setAccountingDirector(String accountingDirector) {
		this.accountingDirector = accountingDirector;
	}
	
	@Length(min=0, max=255, message="公司会计机构负责人长度必须介于 0 和 255 之间")
	public String getOrganizationAccountingDirector() {
		return organizationAccountingDirector;
	}

	public void setOrganizationAccountingDirector(String organizationAccountingDirector) {
		this.organizationAccountingDirector = organizationAccountingDirector;
	}
	
	public String getDirectorSupervisorTopmanagerIntro() {
		return directorSupervisorTopmanagerIntro;
	}

	public void setDirectorSupervisorTopmanagerIntro(String directorSupervisorTopmanagerIntro) {
		this.directorSupervisorTopmanagerIntro = directorSupervisorTopmanagerIntro;
	}
	
	@Length(min=0, max=255, message="主承销商1长度必须介于 0 和 255 之间")
	public String getLeadUnderwriter1() {
		return leadUnderwriter1;
	}

	public void setLeadUnderwriter1(String leadUnderwriter1) {
		this.leadUnderwriter1 = leadUnderwriter1;
	}
	
	@Length(min=0, max=255, message="主承销商2长度必须介于 0 和 255 之间")
	public String getLeadUnderwriter2() {
		return leadUnderwriter2;
	}

	public void setLeadUnderwriter2(String leadUnderwriter2) {
		this.leadUnderwriter2 = leadUnderwriter2;
	}
	
	@Length(min=0, max=255, message="资信评级机构长度必须介于 0 和 255 之间")
	public String getCreditRatingAgencies() {
		return creditRatingAgencies;
	}

	public void setCreditRatingAgencies(String creditRatingAgencies) {
		this.creditRatingAgencies = creditRatingAgencies;
	}
	
	@Length(min=0, max=255, message="发布时间长度必须介于 0 和 255 之间")
	public String getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}
	
	@Length(min=0, max=255, message="收费长度必须介于 0 和 255 之间")
	public String getCharge() {
		return charge;
	}

	public void setCharge(String charge) {
		this.charge = charge;
	}
	
	public String getEngageDismissAccountingFirm() {
		return engageDismissAccountingFirm;
	}

	public void setEngageDismissAccountingFirm(String engageDismissAccountingFirm) {
		this.engageDismissAccountingFirm = engageDismissAccountingFirm;
	}
	
}