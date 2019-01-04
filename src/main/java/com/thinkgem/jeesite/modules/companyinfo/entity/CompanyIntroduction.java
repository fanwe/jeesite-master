/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.companyinfo.entity;

import org.hibernate.validator.constraints.Length;

import com.thinkgem.jeesite.common.persistence.DataEntity;

/**
 * 公司情况Entity
 * @author zhb
 * @version 2018-12-08
 */
public class CompanyIntroduction extends DataEntity<CompanyIntroduction> {
	
	private static final long serialVersionUID = 1L;
	private String fileTitle;		// 文件标题
	private String companyLegalChineseName;		// 公司法定中文名称
	private String companyLegalEnglishName;		// 公司法定英文名称
	private String businessScope;		// 经营范围
	private String accountingFirm;		// 公司聘用的会计师事务所（为基金进行审计的会计师事务所）
	private String situationIssuedBonds;		// 已发行债券情况
	private String accountingDirector;		// 公司会计负责人
	private String organizationAccountingDirector;		// 公司会计机构负责人
	private String bondIs;		// 已发行债券情况
	private String endListingTop10Holders;		// 期末上市前十的持有人
	
	public CompanyIntroduction() {
		super();
	}

	public CompanyIntroduction(String id){
		super(id);
	}

	@Length(min=0, max=255, message="文件标题长度必须介于 0 和 255 之间")
	public String getFileTitle() {
		return fileTitle;
	}

	public void setFileTitle(String fileTitle) {
		this.fileTitle = fileTitle;
	}
	
	@Length(min=0, max=255, message="公司法定中文名称长度必须介于 0 和 255 之间")
	public String getCompanyLegalChineseName() {
		return companyLegalChineseName;
	}

	public void setCompanyLegalChineseName(String companyLegalChineseName) {
		this.companyLegalChineseName = companyLegalChineseName;
	}
	
	@Length(min=0, max=255, message="公司法定英文名称长度必须介于 0 和 255 之间")
	public String getCompanyLegalEnglishName() {
		return companyLegalEnglishName;
	}

	public void setCompanyLegalEnglishName(String companyLegalEnglishName) {
		this.companyLegalEnglishName = companyLegalEnglishName;
	}
	
	public String getBusinessScope() {
		return businessScope;
	}

	public void setBusinessScope(String businessScope) {
		this.businessScope = businessScope;
	}
	
	@Length(min=0, max=255, message="公司聘用的会计师事务所（为基金进行审计的会计师事务所）长度必须介于 0 和 255 之间")
	public String getAccountingFirm() {
		return accountingFirm;
	}

	public void setAccountingFirm(String accountingFirm) {
		this.accountingFirm = accountingFirm;
	}
	
	public String getSituationIssuedBonds() {
		return situationIssuedBonds;
	}

	public void setSituationIssuedBonds(String situationIssuedBonds) {
		this.situationIssuedBonds = situationIssuedBonds;
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
	
	public String getBondIs() {
		return bondIs;
	}

	public void setBondIs(String bondIs) {
		this.bondIs = bondIs;
	}
	
	@Length(min=0, max=255, message="期末上市前十的持有人长度必须介于 0 和 255 之间")
	public String getEndListingTop10Holders() {
		return endListingTop10Holders;
	}

	public void setEndListingTop10Holders(String endListingTop10Holders) {
		this.endListingTop10Holders = endListingTop10Holders;
	}
	
}