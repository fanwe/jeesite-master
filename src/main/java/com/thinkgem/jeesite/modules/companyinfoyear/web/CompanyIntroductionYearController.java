/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.companyinfoyear.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.company.utiles.ParsePDFFileUtile;
import com.thinkgem.jeesite.modules.companyinfoyear.entity.CompanyIntroductionYear;
import com.thinkgem.jeesite.modules.companyinfoyear.service.CompanyIntroductionYearService;

/**
 * 公司年报Controller
 * @author zhb
 * @version 2018-12-16
 */
@Controller
@RequestMapping(value = "${adminPath}/companyinfoyear/companyIntroductionYear")
public class CompanyIntroductionYearController extends BaseController {

	@Autowired
	private CompanyIntroductionYearService companyIntroductionYearService;

	@ModelAttribute
	public CompanyIntroductionYear get(@RequestParam(required=false) String id) {
		CompanyIntroductionYear entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = companyIntroductionYearService.get(id);
		}
		if (entity == null){
			entity = new CompanyIntroductionYear();
		}
		return entity;
	}

	@RequiresPermissions("companyinfoyear:companyIntroductionYear:view")
	@RequestMapping(value = {"list", ""})
	public String list(CompanyIntroductionYear companyIntroductionYear, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<CompanyIntroductionYear> page = companyIntroductionYearService.findPage(new Page<CompanyIntroductionYear>(request, response), companyIntroductionYear); 
		model.addAttribute("page", page);
		return "modules/companyinfoyear/companyIntroductionYearList";
	}

	@RequiresPermissions("companyinfoyear:companyIntroductionYear:view")
	@RequestMapping(value = "form")
	public String form(CompanyIntroductionYear companyIntroductionYear, Model model) {
		model.addAttribute("companyIntroductionYear", companyIntroductionYear);
		return "modules/companyinfoyear/companyIntroductionYearForm";
	}

	@RequiresPermissions("companyinfoyear:companyIntroductionYear:edit")
	@RequestMapping(value = "save")
	public String save(CompanyIntroductionYear companyIntroductionYear, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, companyIntroductionYear)){
			return form(companyIntroductionYear, model);
		}
		companyIntroductionYearService.save(companyIntroductionYear);
		addMessage(redirectAttributes, "保存公司年报成功");
		return "redirect:"+Global.getAdminPath()+"/companyinfoyear/companyIntroductionYear/?repage";
	}
	@RequiresPermissions("companyinfoyear:companyIntroductionYear:edit")
	@RequestMapping(value = "insert")
	public String insert() {
		//1.获取所有的pdf文件
		String path="D:\\第8批次\\";
		//String path="D:\\PDF文件\\匹配不到的字段所在的文件\\公司中文名称\\";
		String[] pdfList = ParsePDFFileUtile.getPDFList(path);
		System.out.println("==================="+pdfList.length+"=========================================");
		//有问题的文件存放的目录
		String pdfForImgSrc = "D:\\问题文件\\";
		//遍历pdf文件夹里面的文件列表
		for (int i = 0; i < pdfList.length; i++) {
			int findByRecordCount = this.companyIntroductionYearService.findByRecordCount(pdfList[i]);
			if(findByRecordCount>1){
				logger.debug("文件["+pdfList[i]+"]数据库中已存在！");
				continue;
			}else{
				//一、创建【年报】对象
				//二、获取PDDocument对象
				Map<String, Object> pdDocumentMap = ParsePDFFileUtile.getPDDocument(path+pdfList[i], path, pdfList[i]);
				//判断 PDDocument 对象是否为 null ,如果不为null则对pdf文档进行解析
				if(pdDocumentMap.get("msg")!=ParsePDFFileUtile.PDF_FILE_CODE_FAIL){
					PDDocument pdDocument = (PDDocument) pdDocumentMap.get("msg");
					//三、解析pdf文件
					String pdfContent = ParsePDFFileUtile.getPDFContent(pdDocument);

					//四、判断 pdfContent是否为null，
					if(pdfContent!=null && pdfContent!=ParsePDFFileUtile.PDF_FILE_CODE_FAIL){
						if(pdfContent.trim().length()>0){
							if(!pdfContent.contains("[] tnt")){
								try {


									CompanyIntroductionYear companyYear = new CompanyIntroductionYear();
									logger.debug("正在获取["+path+pdfList[i]+"]中的数据.........");
									companyYear.setFileName(pdfList[i]);
									//①获取中文名称
									String chineseNameInfo = ParsePDFFileUtile.getChineseNameInfo(pdfContent, pdfList[i]);
									String chineseName = null;
									if(chineseNameInfo!=null && chineseNameInfo!=ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL){
										if(chineseNameInfo.contains("：")){
											int indexOf = chineseNameInfo.indexOf("：")+1;
											chineseName = chineseNameInfo.substring(indexOf, chineseNameInfo.length());
										}else if(chineseNameInfo.contains(" ")){
											int indexOf = chineseNameInfo.indexOf(" ")+1;
											chineseName = chineseNameInfo.substring(indexOf, chineseNameInfo.length());
										}
										companyYear.setChineseNameOfCompany(chineseName);
										logger.debug("["+ParsePDFFileUtile.CHINESE_NAME_OF_COMPANY+"]匹配成功");

									}else{
										logger.debug("【"+pdfList[i]+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.CHINESE_NAME_OF_COMPANY);
										ParsePDFFileUtile.errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.FILED_TO_PATH+ParsePDFFileUtile.CHINESE_NAME_OF_COMPANY+"\\",pdfList[i]);
									}


									//②.获取标题
									if(chineseName!=null && chineseName!=ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL){

										String title = ParsePDFFileUtile.getTitleInfo(pdfContent,chineseName);
										if(title!=null &&title !=ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL ){
											companyYear.setTitle(title);
											logger.debug("["+ParsePDFFileUtile.TITLE_OF_COMPANY+"]匹配成功");
										}else {
											logger.debug("【"+pdfList[i]+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.TITLE_OF_COMPANY);
											ParsePDFFileUtile.errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.FILED_TO_PATH+ParsePDFFileUtile.TITLE_OF_COMPANY+"\\",pdfList[i]);
										}

									}else {
										logger.debug("【"+pdfList[i]+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.TITLE_OF_COMPANY);
										ParsePDFFileUtile.errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.FILED_TO_PATH+ParsePDFFileUtile.TITLE_OF_COMPANY+"\\",pdfList[i]);
									}


									//③获取英文名称
									if(chineseNameInfo!=null && chineseNameInfo!=ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL){
										String englishNameInfo = ParsePDFFileUtile.getEnglishNameInfo(pdfContent,chineseNameInfo );
										if(englishNameInfo!=null && englishNameInfo!=ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL){
											companyYear.setEnglishNameOfCompany(englishNameInfo);
											logger.debug("["+ParsePDFFileUtile.ENGLISH_NAME_OF_COMPANY+"]匹配成功");

										}else {
											logger.debug("【"+pdfList[i]+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.ENGLISH_NAME_OF_COMPANY);
											ParsePDFFileUtile.errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.FILED_TO_PATH+ParsePDFFileUtile.ENGLISH_NAME_OF_COMPANY+"\\",pdfList[i]);
										}

									}else {
										logger.debug("【"+pdfList[i]+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.ENGLISH_NAME_OF_COMPANY);
										ParsePDFFileUtile.errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.FILED_TO_PATH+ParsePDFFileUtile.ENGLISH_NAME_OF_COMPANY+"\\",pdfList[i]);
									}


									//④获取会计师事务所
									String accountingFirmInfo = ParsePDFFileUtile.getAccountingFirmInfo(pdfContent);
									if(accountingFirmInfo!=null && accountingFirmInfo != ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL){
										companyYear.setAccountingFirm(accountingFirmInfo);
										logger.debug("["+ParsePDFFileUtile.ACCOUNTING_FIRM_OF_COMPANY+"]匹配成功");
									}else {
										logger.debug("【"+pdfList[i]+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.ACCOUNTING_FIRM_OF_COMPANY);
										ParsePDFFileUtile.errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.FILED_TO_PATH+ParsePDFFileUtile.ACCOUNTING_FIRM_OF_COMPANY+"\\",pdfList[i]);
									}



									//⑤中国注册会计师
									Set<String> chinese_CPAs = ParsePDFFileUtile.getChinese_CPA(pdfContent);
									if(chinese_CPAs.size()>0){
										int count = 0;
										for (String chinese_CPA : chinese_CPAs) {
											if(count==0){
												companyYear.setChineseCpa1(chinese_CPA);
												logger.debug("["+ParsePDFFileUtile.CHINESE_CPA+count+"]匹配成功");
											}else if (count==1) {
												companyYear.setChineseCpa2(chinese_CPA);
												logger.debug("["+ParsePDFFileUtile.CHINESE_CPA+count+"]匹配成功");
											}else {
												logger.debug("【"+pdfList[i]+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.CHINESE_CPA);
												ParsePDFFileUtile.errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.FILED_TO_PATH+ParsePDFFileUtile.CHINESE_CPA+"\\",pdfList[i]);
												continue;
											}
											count =count+1;
										}
									}else {
										logger.debug("【"+pdfList[i]+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.CHINESE_CPA);
										ParsePDFFileUtile.errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.FILED_TO_PATH+ParsePDFFileUtile.CHINESE_CPA+"\\",pdfList[i]);
									}
									//⑥公司会计负责人
									String accountingDirectorInfo = ParsePDFFileUtile.getAccountingDirectorInfo(pdfContent);
									if(accountingDirectorInfo!=null && accountingDirectorInfo!=ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL){
										companyYear.setAccountingDirector(accountingDirectorInfo);
										logger.debug("["+ParsePDFFileUtile.ACCOUNTING_BIRECTOR_OF_COMPANY+"]匹配成功");
									}else {
										logger.debug("【"+pdfList[i]+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.ACCOUNTING_BIRECTOR_OF_COMPANY);
										ParsePDFFileUtile.errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.FILED_TO_PATH+ParsePDFFileUtile.ACCOUNTING_BIRECTOR_OF_COMPANY+"\\",pdfList[i]);
									}



									//⑦公司会计机构负责人
									String organizationAccountingDirectorInfo = ParsePDFFileUtile.getOrganizationAccountingDirectorInfo(pdfContent);
									if(organizationAccountingDirectorInfo!=null && organizationAccountingDirectorInfo!=ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL){
										companyYear.setOrganizationAccountingDirector(organizationAccountingDirectorInfo);
										logger.debug("["+ParsePDFFileUtile.ORGANIZATION_CCOUNTING_BIRECTOR_OF_COMPANY+"]匹配成功");
									}else {
										logger.debug("【"+pdfList[i]+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.ORGANIZATION_CCOUNTING_BIRECTOR_OF_COMPANY);
										ParsePDFFileUtile.errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.FILED_TO_PATH+ParsePDFFileUtile.ORGANIZATION_CCOUNTING_BIRECTOR_OF_COMPANY+"\\",pdfList[i]);
									}



									//⑧公司董事、监事、高管简介
									String director_supervisor_topManager_intro = ParsePDFFileUtile.getDirector_supervisor_topManager_introInfo(pdDocument, pdfContent);
									if(director_supervisor_topManager_intro!=null && director_supervisor_topManager_intro!=ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL){
										companyYear.setDirectorSupervisorTopmanagerIntro(director_supervisor_topManager_intro);
										logger.debug("["+ParsePDFFileUtile.DIRECTOR_SUPERVISOR_TOPMANAGER_INTOR+"]匹配成功");

									}else {
										logger.debug("【"+pdfList[i]+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.DIRECTOR_SUPERVISOR_TOPMANAGER_INTOR);
										ParsePDFFileUtile.errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.FILED_TO_PATH+ParsePDFFileUtile.DIRECTOR_SUPERVISOR_TOPMANAGER_INTOR+"\\",pdfList[i]);
									}



									//⑨主承销商
									String lead_underwriter = ParsePDFFileUtile.getLead_underwriterInfo(pdfContent);
									if(lead_underwriter!=null &&lead_underwriter!=ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL){
										companyYear.setLeadUnderwriter1(lead_underwriter);
										logger.debug("["+ParsePDFFileUtile.LEAD_UNDERWRITER+"]匹配成功");
									}else {
										logger.debug("【"+pdfList[i]+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.LEAD_UNDERWRITER);
										ParsePDFFileUtile.errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.FILED_TO_PATH+ParsePDFFileUtile.LEAD_UNDERWRITER+"\\",pdfList[i]);
									}



									//⑩资信评级机构
									String credit_rating_agencies = ParsePDFFileUtile.getCredit_rating_agenciesInfo(pdfContent);
									if(credit_rating_agencies!=null && credit_rating_agencies!=ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL){
										companyYear.setCreditRatingAgencies(credit_rating_agencies);
										logger.debug("["+ParsePDFFileUtile.GREDIT_RATING_AGENCIES+"]匹配成功");
									}else {
										logger.debug("【"+pdfList[i]+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.GREDIT_RATING_AGENCIES);
										ParsePDFFileUtile.errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.FILED_TO_PATH+ParsePDFFileUtile.GREDIT_RATING_AGENCIES+"\\",pdfList[i]);
									}
									//-聘用、解聘会计师事务所情况

									String engage_dismiss_accounting_firm2 = ParsePDFFileUtile.getEngage_dismiss_accounting_firmInfo(pdDocument,pdfContent);
									if(engage_dismiss_accounting_firm2!=null && engage_dismiss_accounting_firm2 !=ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL){
										companyYear.setEngageDismissAccountingFirm(engage_dismiss_accounting_firm2);
										logger.debug("["+engage_dismiss_accounting_firm2+"]匹配成功");
									}else {
										logger.debug("【"+pdfList[i]+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.ENGAGE_DISMISS_ACCOUNTING_FIRM);
										ParsePDFFileUtile.errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.FILED_TO_PATH+ParsePDFFileUtile.ENGAGE_DISMISS_ACCOUNTING_FIRM+"\\",pdfList[i]);
									}
									//向数据库插入年报对象
									this.companyIntroductionYearService.save(companyYear);
									ParsePDFFileUtile.errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.PROSS_SUCCESS_FILE+"\\",pdfList[i]);
									//FileUtils.deleteFile(path+pdfList[i]);
									
								} catch (Exception e) {
									ParsePDFFileUtile.errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.EXCEPTION_FILE,pdfList[i]);
									logger.debug("错误文件位置：【"+pdfList[i]+"】",e);
								}
								System.out.println("===========================================================第"+i+"个文件=======================================================================");
								//	System.out.println(companyYear.toString());
								//companyIntroductionYearService.save(companyYear);
							}else {
								ParsePDFFileUtile.errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.UNREADABLE_CODE,pdfList[i]);
								logger.debug("【"+pdfList[i]+"】文件为乱码图片文件");
								continue;
							}





						}else {
							logger.debug("PDF文件内容为图片");
							//存放都为图片内容的pdf文件
							ParsePDFFileUtile.errorFiledPross(path+pdfList[i], pdfForImgSrc+ParsePDFFileUtile.FILE_CONTENT_IMAGE, pdfList[i]);
							continue;
						}
					}else {
						ParsePDFFileUtile.errorFiledPross(path+pdfList[i], pdfForImgSrc+ParsePDFFileUtile.FILE_CONTENT_FAIL, pdfList[i]);
						logger.debug("PDF文件内容已损坏!");
						continue;

					}

				}else {
					ParsePDFFileUtile.errorFiledPross(path+pdfList[i], pdfForImgSrc+ParsePDFFileUtile.FILE_CONTENT_FAIL, pdfList[i]);
					logger.debug("PDF文件内容已损坏!");
					continue;
				}
			}
		}
		return "redirect:"+Global.getAdminPath()+"/companyinfoyear/companyIntroductionYear/?repage";

	}

	@RequiresPermissions("companyinfoyear:companyIntroductionYear:edit")
	@RequestMapping(value = "delete")
	public String delete(CompanyIntroductionYear companyIntroductionYear, RedirectAttributes redirectAttributes) {
		companyIntroductionYearService.delete(companyIntroductionYear);
		addMessage(redirectAttributes, "删除公司年报成功");
		return "redirect:"+Global.getAdminPath()+"/companyinfoyear/companyIntroductionYear/?repage";
	}

}