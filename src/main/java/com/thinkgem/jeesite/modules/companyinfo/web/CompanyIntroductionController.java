///**
// * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
// */
//package com.thinkgem.jeesite.modules.companyinfo.web;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.InputStream;
//import java.net.URL;
//import java.net.URLConnection;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.shiro.authz.annotation.RequiresPermissions;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import com.thinkgem.jeesite.common.config.Global;
//import com.thinkgem.jeesite.common.persistence.Page;
//import com.thinkgem.jeesite.common.web.BaseController;
//import com.thinkgem.jeesite.common.utils.FileUtils;
//import com.thinkgem.jeesite.common.utils.StringUtils;
////import com.thinkgem.jeesite.modules.company.utiles.ParsePDFFileUtile;
//import com.thinkgem.jeesite.modules.companyinfo.entity.CompanyIntroduction;
//import com.thinkgem.jeesite.modules.companyinfo.service.CompanyIntroductionService;
//import com.thinkgem.jeesite.modules.error.entity.kuaijishishiwusuo.Errorfile;
//import com.thinkgem.jeesite.modules.error.service.kuaijishishiwusuo.ErrorfileService;
//
///**
// * 公司情况Controller
// * @author zhb
// * @version 2018-12-08
// */
//@Controller
//@RequestMapping(value = "${adminPath}/companyinfo/companyIntroduction")
//public class CompanyIntroductionController extends BaseController {
//
//	@Autowired
//	private CompanyIntroductionService companyIntroductionService;
//	@Autowired
//	private ErrorfileService erorfileService;
//	@ModelAttribute
//	public CompanyIntroduction get(@RequestParam(required=false) String id) {
//		CompanyIntroduction entity = null;
//		if (StringUtils.isNotBlank(id)){
//			entity = companyIntroductionService.get(id);
//		}
//		if (entity == null){
//			entity = new CompanyIntroduction();
//		}
//		return entity;
//	}
//
//	@RequiresPermissions("companyinfo:companyIntroduction:view")
//	@RequestMapping(value = {"list", ""})
//	public String list(CompanyIntroduction companyIntroduction, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<CompanyIntroduction> page = companyIntroductionService.findPage(new Page<CompanyIntroduction>(request, response), companyIntroduction); 
//		model.addAttribute("page", page);
//		return "modules/companyinfo/companyIntroductionList";
//	}
//
//	@RequiresPermissions("companyinfo:companyIntroduction:view")
//	@RequestMapping(value = "form")
//	public String form(CompanyIntroduction companyIntroduction, Model model) {
//		model.addAttribute("companyIntroduction", companyIntroduction);
//		return "modules/companyinfo/companyIntroductionForm";
//	}
//
//	@RequiresPermissions("companyinfo:companyIntroduction:edit")
//	@RequestMapping(value = "save")
//	public String save(CompanyIntroduction companyIntroduction, Model model, RedirectAttributes redirectAttributes) {
//		if (!beanValidator(model, companyIntroduction)){
//			return form(companyIntroduction, model);
//		}
//		companyIntroductionService.save(companyIntroduction);
//		addMessage(redirectAttributes, "保存公司情况成功");
//		return "redirect:"+Global.getAdminPath()+"/companyinfo/companyIntroduction/?repage";
//	}
//	@RequiresPermissions("companyinfo:companyIntroduction:edit")
//	@RequestMapping(value = "insert")
//	public void insert(Model model, RedirectAttributes redirectAttributes) throws IOException {
//		//String fileSrc = "C:\\Users\\DELL\\Desktop\\caitong\\";
//		String fileSrc = "C:\\Users\\DELL\\Desktop\\巨潮资讯\\";
//		String[] pdfFileNameArr = ParsePDFFileUtile.getPDFList(fileSrc);
//		if(pdfFileNameArr!=null &&pdfFileNameArr.length>0){
//
//
//			System.out.println("文件数量为:"+pdfFileNameArr.length);
//			System.out.println("=======================================开始============================================================================");
//			PDDocument pdDocument =null;
//			Map<String, String> subTitleStr =null;
//			String subTitle=null;
//			String pdfContent =null;
//			for (int i = 0; i<pdfFileNameArr.length ; i++) {
//				CompanyIntroduction companyIntroduction1 = companyIntroductionService.getFileName(pdfFileNameArr[i]);
//				if(companyIntroduction1==null){
//					try{
//						CompanyIntroduction companyIntroduction= new CompanyIntroduction();
//						subTitleStr = ParsePDFFileUtile.subStr(pdfFileNameArr[i]);
//						subTitle = subTitleStr.get("msg");
//
//						pdDocument = (PDDocument) ParsePDFFileUtile.getPDDocument(fileSrc+pdfFileNameArr[i],fileSrc,pdfFileNameArr[i]);
//						if(pdDocument!=null){
//
//
//							//pdf文件转换后的文本内容
//							pdfContent = ParsePDFFileUtile.getPDFContent(pdDocument);
//
//							companyIntroduction.setFileTitle(pdfFileNameArr[i]);
//							/**
//							 * 公司名称、文章标题的正则匹配与过滤后的文本输出
//
//
//					//正则规则（file_title）=>对应数据库字段
//					String name="公司中文名称";
//					String regex = "公司中文全称\\s*.[\u4e00-\u9fa5].*公司";
//					Map<String, String> titleFromRegex = ParsePDFFileUtile.getValueFromRegex(pdfContent, regex,fileSrc+pdfFileNameArr[i],name);
//					//公司中文名称
//					String company = titleFromRegex.get("msg");
//					if(subTitle!=null){
//						Map<String, String> subStr = ParsePDFFileUtile.subStr(company," ");
//						String companyName = subStr.get("msg");
//						System.out.println(name+"==>"+companyName);
//						//标题
//						String title1 ="标题";
//						String title  = companyName+subTitle;
//						System.out.println("标题=>"+title);
//
//						companyIntroduction.setFileTitle(title);
//						companyIntroduction.setCompanyLegalChineseName(companyName);
//					}else{
//						companyIntroduction.setFileTitle(company);
//					}*/
//
//
//
//
//							/**
//							 * 
//
//
//
//					//公司法定英文名称
//					String name1="公司法定英文名称";
//					String regex1 = "英文名称及缩写\\s*.*";
//					Map<String, String> englishName = ParsePDFFileUtile.getValueFromRegex(pdfContent, regex1,fileSrc+pdfFileNameArr[i],name1);
//					String englishNames = englishName.get("msg");
//					Map<String, String> englishNameStr = ParsePDFFileUtile.subStr(englishNames," ");
//					String englishname = englishNameStr.get("msg");
//					System.out.println("公司法定英文名称=>"+englishname);
//					companyIntroduction.setCompanyLegalEnglishName(englishname);
//
//					
//
//
//					//已发行债券情况****
//					if(pdfContent.contains("发行债券")||pdfContent.contains("可转换债券")){
//						String name3="已发行债券情况";
//						String regex3 = ".*.发行债券.*";
//						Map<String, String> situationIssuedBonds = ParsePDFFileUtile.getValueFromRegex(pdfContent, regex3,fileSrc+pdfFileNameArr[i],name3);
//						String situationIssuedBondsName = situationIssuedBonds.get("msg");
//						if(situationIssuedBondsName==null){
//							String regexzaiquan = ".*.可转换债券.*";
//							Map<String, String> situationIssuedBond = ParsePDFFileUtile.getValueFromRegex(pdfContent, regexzaiquan,fileSrc+pdfFileNameArr[i],name3);
//							String situationIssuedBondszaiquan = situationIssuedBond.get("msg");
//							System.out.println("已发行债券情况=>"+situationIssuedBondszaiquan);
//
//							companyIntroduction.setSituationIssuedBonds(situationIssuedBondszaiquan);
//
//						}else {
//
//							System.out.println("已发行债券情况=>"+situationIssuedBondsName);
//						}
//					}
//							 */
//							//getAccountingFirmInfo(fileSrc, pdfFileNameArr, pdfContent, i, companyIntroduction);
//
//
//
////							getAccountingDirectorInfo(fileSrc, pdfFileNameArr, pdfContent, i, companyIntroduction);
////
////							getOrganizationAccountingDirectorInfo(fileSrc, pdfFileNameArr, pdfContent, i,
////									companyIntroduction);
//
//
//							//			//已发行债券情况
//							//			String regex6 = "英文名称及缩写\\s*[A-Z]\\。$";
//							//			Map<String, String> bondIs = ParsePDFFileUtile.getValueFromRegex(pdfContent, regex6,file+pdfFileNameArr[i]);
//							//			String bondIsName = bondIs.get("msg");
//							//			System.out.println("已发行债券情况=>"+bondIsName);
//
//							//期末上市前十的持有人
//							if(pdfContent.contains("前十")||pdfContent.contains("前五")){
//								
//							}
//							String regex7 = "(前?五|十).*.股东情况.*.单位：股";
//							String name6="期末上市前十的持有人";
//							Map<String, String> end_listing_top10_holders = ParsePDFFileUtile.getValueFromRegex(pdfContent, regex7);
//							String end_listing_top10_holdersName = end_listing_top10_holders.get("msg");
//							System.out.println("期末上市前十的持有人=>"+end_listing_top10_holdersName);
//							//	companyIntroduction.setEndListingTop10Holders(null);
//							companyIntroductionService.save(companyIntroduction);
//							System.out.println("================insert success"+(i+1)+"===================");
//							System.out.println("=======================================结束=======匹配第"+(i+1)+"个pdf文件=====================================================================");
//						}
//
//						else{
//							logger.debug("此pdf文件不可用");
//						}
//					}catch(IllegalArgumentException e){
//						logger.debug("异常", e);;
//					}
//
//					//addMessage(redirectAttributes, "保存公司情况成功");
//				}else {
//					logger.debug("【"+pdfFileNameArr[i]+"】已经存在数据库！");
//				}
//			}
//		}else {
//			logger.debug("pdf文件不存在");
//		}
//		//return "redirect:"+Global.getAdminPath()+"/companyinfo/companyIntroduction/?repage";
//	}
//
//	
//	
//
//	
//	@RequiresPermissions("companyinfo:companyIntroduction:edit")
//	@RequestMapping(value = "delete")
//	public String delete(CompanyIntroduction companyIntroduction, RedirectAttributes redirectAttributes) {
//		companyIntroductionService.delete(companyIntroduction);
//		addMessage(redirectAttributes, "删除公司情况成功");
//		return "redirect:"+Global.getAdminPath()+"/companyinfo/companyIntroduction/?repage";
//	}
//
//}