package com.thinkgem.jeesite.modules.company.utiles;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.thinkgem.jeesite.common.utils.FileUtils;
import com.thinkgem.jeesite.modules.companyinfoyear.entity.CompanyIntroductionYear;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class ParsePDFFileUtile{
	protected static Logger logger = LoggerFactory.getLogger(ParsePDFFileUtile.class);
	//半年报字段
	public static final String CHINESE_NAME_OF_COMPANY="公司中文名称";
	public static final String ENGLISH_NAME_OF_COMPANY="公司英文名称";
	public static final String TITLE_OF_COMPANY="文本标题";
	public static final String ACCOUNTING_FIRM_OF_COMPANY="公司聘用的会计师事务所";
	public static final String ACCOUNTING_BIRECTOR_OF_COMPANY="主管会计工作负责人";
	public static final String ORGANIZATION_CCOUNTING_BIRECTOR_OF_COMPANY="会计机构负责人";
	public static final String SHAREHOLDER_OF_COMPANY="期末上市前十(前五)的持有人";
	//年报独有字段
	public static final String CHINESE_CPA="中国注册会计师";
	public static final String DIRECTOR_SUPERVISOR_TOPMANAGER_INTOR="公司董事、监事、高管简介";
	public static final String LEAD_UNDERWRITER="主承销商";
	public static final String GREDIT_RATING_AGENCIES="资信评级机构";
	public static final String ENGAGE_DISMISS_ACCOUNTING_FIRM="聘用、解聘会计师事务所情况";
	public static final String PROSS_SUCCESS_FILE ="\\处理成功的文件\\";

	public static final String FILED_TO_PATH="\\匹配不到的字段所在的文件\\";
	public static final String FILE_CONTENT_IMAGE="\\PDF文件内容为图片的文件\\";
	public static final String FILE_CONTENT_FAIL="\\PDF文件内容已损坏\\";
	public static final String UNREADABLE_CODE ="\\图片内容为乱码的文件\\";

	public static final String EXCEPTION_FILE="\\异常文件\\";

	//问题文件返回的 code
	//pdf文件内容为image
	public static final String PDF_FILE_CODE_IMAGE="400";
	//pdf文件损坏
	public static final String PDF_FILE_CODE_FAIL="500";
	//pdf文件解析后为空白字符
	public static final String PDF_FILE_CODE_BLANK="600";

	//字段属性值匹配不到
	public static final String PDF_FILE_CODE_FILED_NULL="700";

	public static void main(String[] args) throws IOException {
		String path = "C:\\Users\\DELL\\Desktop\\异常文件-4\\异常文件\\";
		String[] pdfList = ParsePDFFileUtile.getPDFList(path);
		String pdfSrc="C:\\Users\\DELL\\Desktop\\文件排查测试-4批次第三波排查";
		List<CompanyIntroductionYear> pdfObjectList = ParsePDFFileUtile.getPDFObjectList(path, pdfList, pdfSrc);
		System.out.println("============完！================");
	}

	public static List<CompanyIntroductionYear> getPDFObjectList(String path, String[] pdfList, String pdfForImgSrc) {
		List<CompanyIntroductionYear> list = new ArrayList<CompanyIntroductionYear>();
		if(pdfList.length>0){
			//文件列表的初始数量
			//遍历pdf文件夹里面的文件列表
			for (int i = 0; i < pdfList.length; i++) {
				//一、创建【年报】对象
				//二、获取PDDocument对象
				Map<String, Object> pdDocumentMap = ParsePDFFileUtile.getPDDocument(path+pdfList[i], path, pdfList[i]);
				//判断 PDDocument 对象是否为 null ,如果不为null则对pdf文档进行解析
				if(pdDocumentMap.get("msg")!=PDF_FILE_CODE_FAIL){
					PDDocument pdDocument=null;
					try {
						pdDocument = (PDDocument) pdDocumentMap.get("msg");
						//三、解析pdf文件
						String pdfContent = ParsePDFFileUtile.getPDFContent(pdDocument);

						//四、判断 pdfContent是否为null，
						if(pdfContent!=null && pdfContent!=ParsePDFFileUtile.PDF_FILE_CODE_FAIL){
							if(pdfContent.trim().length()>0){
								if(!pdfContent.contains("[] tnt")){
									CompanyIntroductionYear companyYear = new CompanyIntroductionYear();
									logger.debug("正在获取["+path+pdfList[i]+"]中的数据.........");
									logger.debug("================================="+pdfList.length+"");
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
										logger.debug("["+CHINESE_NAME_OF_COMPANY+"]匹配成功");

									}else{
										logger.debug("【"+pdfList[i]+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.CHINESE_NAME_OF_COMPANY);
										errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.FILED_TO_PATH+ParsePDFFileUtile.CHINESE_NAME_OF_COMPANY+"\\",pdfList[i]);
									}


									//②.获取标题
									if(chineseName!=null && chineseName!=ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL){

										String title = ParsePDFFileUtile.getTitleInfo(pdfContent,chineseName);
										if(title!=null &&title !=ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL ){
											companyYear.setTitle(title);
											logger.debug("["+TITLE_OF_COMPANY+"]匹配成功");
										}else {
											logger.debug("【"+pdfList[i]+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.TITLE_OF_COMPANY);
											errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.FILED_TO_PATH+ParsePDFFileUtile.TITLE_OF_COMPANY+"\\",pdfList[i]);
										}

									}else {
										logger.debug("【"+pdfList[i]+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.TITLE_OF_COMPANY);
										errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.FILED_TO_PATH+ParsePDFFileUtile.TITLE_OF_COMPANY+"\\",pdfList[i]);
									}


									//③获取英文名称
									if(chineseNameInfo!=null && chineseNameInfo!=ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL){
										String englishNameInfo = ParsePDFFileUtile.getEnglishNameInfo(pdfContent,chineseNameInfo );
										if(englishNameInfo!=null && englishNameInfo!=ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL){
											companyYear.setEnglishNameOfCompany(englishNameInfo);
											logger.debug("["+ENGLISH_NAME_OF_COMPANY+"]匹配成功");

										}else {
											logger.debug("【"+pdfList[i]+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.ENGLISH_NAME_OF_COMPANY);
											errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.FILED_TO_PATH+ParsePDFFileUtile.ENGLISH_NAME_OF_COMPANY+"\\",pdfList[i]);
										}

									}else {
										logger.debug("【"+pdfList[i]+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.ENGLISH_NAME_OF_COMPANY);
										errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.FILED_TO_PATH+ParsePDFFileUtile.ENGLISH_NAME_OF_COMPANY+"\\",pdfList[i]);
									}


									//④获取会计师事务所
									String accountingFirmInfo = ParsePDFFileUtile.getAccountingFirmInfo(pdfContent);
									if(accountingFirmInfo!=null && accountingFirmInfo != PDF_FILE_CODE_FILED_NULL){
										companyYear.setAccountingFirm(accountingFirmInfo);
										logger.debug("["+ACCOUNTING_FIRM_OF_COMPANY+"]匹配成功");
									}else {
										logger.debug("【"+pdfList[i]+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.ACCOUNTING_FIRM_OF_COMPANY);
										errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.FILED_TO_PATH+ParsePDFFileUtile.ACCOUNTING_FIRM_OF_COMPANY+"\\",pdfList[i]);
									}
									//⑤中国注册会计师
									Set<String> chinese_CPAs = ParsePDFFileUtile.getChinese_CPA(pdfContent);
									if(chinese_CPAs.size()==0){
										int count = 0;
										for (String chinese_CPA : chinese_CPAs) {
											if(count==0){
												companyYear.setChineseCpa1(chinese_CPA);
												logger.debug("["+CHINESE_CPA+count+"]匹配成功");
											}else if (count==1) {
												companyYear.setChineseCpa2(chinese_CPA);
												logger.debug("["+CHINESE_CPA+count+"]匹配成功");
											}else if (count==2) {
												companyYear.setChineseCpa2(chinese_CPA);
												logger.debug("["+CHINESE_CPA+count+"]匹配成功");
											}else {
												logger.debug("【"+pdfList[i]+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.CHINESE_CPA);
												errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.FILED_TO_PATH+ParsePDFFileUtile.CHINESE_CPA+"\\",pdfList[i]);
												continue;
											}
											count =count+1;
										}
									}else {
										logger.debug("【"+pdfList[i]+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.CHINESE_CPA);
										errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.FILED_TO_PATH+ParsePDFFileUtile.CHINESE_CPA+"\\",pdfList[i]);
									}




									//⑥公司会计负责人
									String accountingDirectorInfo = ParsePDFFileUtile.getAccountingDirectorInfo(pdfContent);
									if(accountingDirectorInfo!=null && accountingDirectorInfo!=ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL){
										companyYear.setAccountingDirector(accountingDirectorInfo);
										logger.debug("["+ACCOUNTING_BIRECTOR_OF_COMPANY+"]匹配成功");
									}else {
										logger.debug("【"+pdfList[i]+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.ACCOUNTING_BIRECTOR_OF_COMPANY);
										errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.FILED_TO_PATH+ParsePDFFileUtile.ACCOUNTING_BIRECTOR_OF_COMPANY+"\\",pdfList[i]);
									}



									//⑦公司会计机构负责人
									String organizationAccountingDirectorInfo = ParsePDFFileUtile.getOrganizationAccountingDirectorInfo(pdfContent);
									if(organizationAccountingDirectorInfo!=null && organizationAccountingDirectorInfo!=ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL){
										companyYear.setOrganizationAccountingDirector(organizationAccountingDirectorInfo);
										logger.debug("["+ORGANIZATION_CCOUNTING_BIRECTOR_OF_COMPANY+"]匹配成功");
									}else {
										logger.debug("【"+pdfList[i]+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.ORGANIZATION_CCOUNTING_BIRECTOR_OF_COMPANY);
										errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.FILED_TO_PATH+ParsePDFFileUtile.ORGANIZATION_CCOUNTING_BIRECTOR_OF_COMPANY+"\\",pdfList[i]);
									}



									//⑧公司董事、监事、高管简介
									String director_supervisor_topManager_intro = ParsePDFFileUtile.getDirector_supervisor_topManager_introInfo(pdDocument, pdfContent);
									if(director_supervisor_topManager_intro!=null && director_supervisor_topManager_intro!=PDF_FILE_CODE_FILED_NULL){
										companyYear.setDirectorSupervisorTopmanagerIntro(director_supervisor_topManager_intro);
										logger.debug("["+DIRECTOR_SUPERVISOR_TOPMANAGER_INTOR+"]匹配成功");

									}else {
										logger.debug("【"+pdfList[i]+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.DIRECTOR_SUPERVISOR_TOPMANAGER_INTOR);
										errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.FILED_TO_PATH+ParsePDFFileUtile.DIRECTOR_SUPERVISOR_TOPMANAGER_INTOR+"\\",pdfList[i]);
									}



									//⑨主承销商
									String lead_underwriter = ParsePDFFileUtile.getLead_underwriterInfo(pdfContent);
									if(lead_underwriter!=null &&lead_underwriter!=PDF_FILE_CODE_FILED_NULL){
										companyYear.setLeadUnderwriter1(lead_underwriter);
										logger.debug("["+LEAD_UNDERWRITER+"]匹配成功");
									}else {
										logger.debug("【"+pdfList[i]+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.LEAD_UNDERWRITER);
										errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.FILED_TO_PATH+ParsePDFFileUtile.LEAD_UNDERWRITER+"\\",pdfList[i]);
									}



									//⑩资信评级机构
									String credit_rating_agencies = ParsePDFFileUtile.getCredit_rating_agenciesInfo(pdfContent);
									if(credit_rating_agencies!=null && credit_rating_agencies!=PDF_FILE_CODE_FILED_NULL){
										companyYear.setCreditRatingAgencies(credit_rating_agencies);
										logger.debug("["+GREDIT_RATING_AGENCIES+"]匹配成功");
									}else {
										logger.debug("【"+pdfList[i]+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.GREDIT_RATING_AGENCIES);
										errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.FILED_TO_PATH+ParsePDFFileUtile.GREDIT_RATING_AGENCIES+"\\",pdfList[i]);
									}
									//-聘用、解聘会计师事务所情况

									String engage_dismiss_accounting_firm2 = ParsePDFFileUtile.getEngage_dismiss_accounting_firmInfo(pdDocument,pdfContent);
									if(engage_dismiss_accounting_firm2!=null && engage_dismiss_accounting_firm2 !=PDF_FILE_CODE_FILED_NULL){
										companyYear.setEngageDismissAccountingFirm(engage_dismiss_accounting_firm2);
										logger.debug("["+engage_dismiss_accounting_firm2+"]匹配成功");
									}else {
										logger.debug("【"+pdfList[i]+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.ENGAGE_DISMISS_ACCOUNTING_FIRM);
										errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.FILED_TO_PATH+ParsePDFFileUtile.ENGAGE_DISMISS_ACCOUNTING_FIRM+"\\",pdfList[i]);
									}
									//向数据库插入年报对象
									System.out.println("==========================================插入第"+i+"条数据成功！=======================================================");
									errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.PROSS_SUCCESS_FILE,pdfList[i]);
									FileUtils.deleteFile(path+pdfList[i]);

									System.out.println("===========================================================第"+i+"个文件=======================================================================");
									//	System.out.println(companyYear.toString());
									//companyIntroductionYearService.save(companyYear);
								}else {
									logger.debug("【"+pdfList[i]+"】文件为乱码图片文件");
									errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.UNREADABLE_CODE,pdfList[i]);
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
					} catch (Exception e) {
						logger.debug("错误文件位置：【"+pdfList[i]+"】",e);
						errorFiledPross(path+pdfList[i],pdfForImgSrc+ParsePDFFileUtile.EXCEPTION_FILE,pdfList[i]);
						FileUtils.delFile(path+pdfList[i]);
					}finally{
						if(null!=pdDocument){
							try {
								pdDocument.close();
							} catch (IOException e) {
								logger.debug("PDFDocument对象没有关闭！",e);

							}
						}
					}

				}else {
					ParsePDFFileUtile.errorFiledPross(path+pdfList[i], pdfForImgSrc+ParsePDFFileUtile.FILE_CONTENT_FAIL, pdfList[i]);
					logger.debug("PDF文件内容已损坏!");
					continue;
				}

			}

		}else {
			logger.debug("文件列表为空");
		}

		return list;

	}

	/********************************************************年报告中与半年报告中不同的字段方法===========开始**********************************************************************************************************************/

	@Test
	public void  blank(){
		PDDocument pdDocument = (PDDocument) ParsePDFFileUtile.getPDDocument("C:\\Users\\DELL\\Desktop\\bb\\02电网15：2015年年度报告.pdf", "C:\\Users\\DELL\\Desktop\\bb\\", "02电网15：2015年年度报告.pdf");
		if(pdDocument!=null){
			String pdfContent = ParsePDFFileUtile.getPDFContent(pdDocument);
			if(pdfContent!=ParsePDFFileUtile.PDF_FILE_CODE_FAIL && pdfContent!=null){
				System.out.println(pdfContent+"aaaaaaaa");
			}else {
				System.out.println(pdfContent);
			}
		}
	}



	@Test
	public void  numParseChinesenumTest(){
		String numParseChinesenum = numParseChinesenum(0);
		System.out.println(numParseChinesenum);
	}

	@Test
	public void  getChinesenumParseNumTest(){
		int chinesenumParseNum = chinesenumParseNum("你好");
		if(chinesenumParseNum==0){
			System.out.println("不能转换");
		}else {
			System.out.println(chinesenumParseNum+2);

		}
	}
	/**
	 * 测试：公司中文名称
	 */
	@Test
	public void chinese_name_test(){
		String path = "C:\\Users\\DELL\\Desktop\\公司中文名称\\";
		String[] pdfList = ParsePDFFileUtile.getPDFList(path);
		String pdfSrc="C:\\Users\\DELL\\Desktop\\公司中文名称-问题文件\\";
		for (String chinesename : pdfList) {
			PDDocument pdDocument = (PDDocument) ParsePDFFileUtile.getPDDocument(path+chinesename, path,chinesename);
			String pdfContent = ParsePDFFileUtile.getPDFContent(pdDocument);
			String chineseNameInfo = ParsePDFFileUtile.getChineseNameInfo(pdfContent, chinesename);
			System.out.println(chineseNameInfo);
		}
	}
	//测试：中国注册会计师1 、2
	@Test
	public void  getChinese_CPA_1Test(){
		System.out.println("====================");
		//1.获取所有的pdf文件
		String path="C:\\Users\\DELL\\Desktop\\中国注册会计师\\";
		//				Map<String, Object> pdDocument = ParsePDFFileUtile.getPDDocument(path+"[定期报告]爱廸新能-2015年年度报告.pdf", path,"[定期报告]爱廸新能-2015年年度报告.pdf");
		//				PDDocument pdd = (PDDocument) pdDocument.get("msg");
		//				String pdfContent = ParsePDFFileUtile.getPDFContent(pdd);
		//				System.out.println(pdfContent);
		String[] pdfList = ParsePDFFileUtile.getPDFList(path);
		//			//	System.out.println("==================="+pdfList.length+"=========================================");
		//
		//有问题的文件存放的目录
		String pdfForImgSrc = "C:\\Users\\DELL\\Desktop\\年报\\问题文件";
		for (String fileName : pdfList) {
			System.out.println(fileName);
			Map<String, Object> pdDocument = ParsePDFFileUtile.getPDDocument(path+fileName, path,fileName);
			PDDocument pdd = (PDDocument) pdDocument.get("msg");
			String pdfContent = ParsePDFFileUtile.getPDFContent(pdd);
			Set<String> chinese_CPA2 = ParsePDFFileUtile.getChinese_CPA(pdfContent);
			System.out.println(chinese_CPA2);


		}

		//		Map<String, Object> pdDocument = ParsePDFFileUtile.getPDDocument(path+"[定期报告]丰海科技-2017年年度报告.pdf", path,"[定期报告]丰海科技-2017年年度报告.pdf");
		//		PDDocument pdd = (PDDocument) pdDocument.get("msg");
		//		String pdfContent = ParsePDFFileUtile.getPDFContent(pdd);
		//		Set<String> chinese_CPA2 = ParsePDFFileUtile.getChinese_CPA(pdfContent);
		//		System.out.println(chinese_CPA2);
	}

	//测试：公司董事、监事、高管简介
	@Test
	public void  getDirector_supervisor_topManager_introTest(){
		System.out.println("===========================开始========================================");
		String path = "C:\\Users\\DELL\\Desktop\\处理成功的文件\\";
		String[] pdfList = ParsePDFFileUtile.getPDFList(path);
		for (String fileName : pdfList) {
			Map<String, Object> pdDocument = ParsePDFFileUtile.getPDDocument(path+fileName, path,fileName);
			PDDocument pdd = (PDDocument) pdDocument.get("msg");
			String pdfContent = ParsePDFFileUtile.getPDFContent(pdd);
			String director_supervisor_topManager_intro = ParsePDFFileUtile.getDirector_supervisor_topManager_introInfo(pdd,pdfContent);
			System.out.println(director_supervisor_topManager_intro);

		}

		System.out.println("===========================结束========================================");

	}
	//测试：主承销商1
	@Test
	public void  getLead_underwriter_1Test(){
		System.out.println("===========================开始========================================");
		String path = "C:\\Users\\DELL\\Desktop\\aa\\";
		String fileName="[临时公告]长油5-2016年年度报告.pdf";
		PDDocument pdDocument = (PDDocument) ParsePDFFileUtile.getPDDocument(path+fileName, path,fileName);
		String pdfContent = ParsePDFFileUtile.getPDFContent(pdDocument);
		String credit_rating_agencies = ParsePDFFileUtile.getLead_underwriterInfo(pdfContent);
		System.out.println(credit_rating_agencies);
	}
	//测试：主承销商2
	@Test
	public void  getLead_underwriter_2Test(){
		System.out.println("====");
	}
	//测试：资信评级机构
	@Test
	public void  getCredit_rating_agenciesTest(){
		System.out.println("===========================开始========================================");
		String path = "C:\\Users\\DELL\\Desktop\\aa\\";
		String fileName="01中移动：2007年年度报告.pdf";
		PDDocument pdDocument = (PDDocument) ParsePDFFileUtile.getPDDocument(path+fileName, path,fileName);
		String pdfContent = ParsePDFFileUtile.getPDFContent(pdDocument);
		String credit_rating_agencies = ParsePDFFileUtile.getCredit_rating_agenciesInfo(pdfContent);
		System.out.println(credit_rating_agencies);
	}
	//测试：发布时间
	@Test
	public void  getRelease_timeTest(){
		System.out.println("====");
	}
	//测试：①聘用、解聘会计师事务所情况 ②收费 
	@Test
	public void getEngage_dismiss_accounting_firmTest(){
		System.out.println("===========================开始========================================");
		String path = "C:\\Users\\DELL\\Desktop\\aa\\";
		String fileName="[临时公告]长油5-2016年年度报告.pdf";
		PDDocument pdDocument = (PDDocument) ParsePDFFileUtile.getPDDocument(path+fileName, path,fileName);
		String pdfContent = ParsePDFFileUtile.getPDFContent(pdDocument);
		String engage_dismiss_accounting_firm = ParsePDFFileUtile.getEngage_dismiss_accounting_firmInfo(pdDocument,pdfContent);
		System.out.println(engage_dismiss_accounting_firm);
	}

	public static String getStr(String str){
		StringBuilder bu = new StringBuilder();
		if(str.contains(" ")){
			String[] split = str.split(" ");
			for (String string : split) {
				bu.append(string);
			}

			return bu.toString();
		}else {
			return str;
		}
	}

	@Test
	public void  contansImage(){
		//图片pdf所在的文件夹
		String imageRootPath="C:\\Users\\DELL\\Desktop\\PDF文件内容为图片的文件\\";
		//pdf文件名称
		String pdfFileName="[定期报告]金银花-2015年年度报告.pdf";
		//存放pdf文件文件所在的目录
		String pdfImagePath="C:\\Users\\DELL\\Desktop\\PDF文件\\";
		Map<String, Object> pdDocument = ParsePDFFileUtile.getPDDocument(
				imageRootPath+pdfFileName,
				imageRootPath,pdfFileName);
		//获取文档页面信息

		if(pdDocument.get("msg")!=ParsePDFFileUtile.PDF_FILE_CODE_FAIL){
			PDDocument document = (PDDocument)pdDocument.get("msg");
			List<PDImageXObject> imageListFromPDF = ParsePDFFileUtile.getImageListFromPDF(document,0);
			String pdFimageForString = getPDFimageForString(imageListFromPDF,pdfImagePath,pdfFileName,null);
			System.out.println(pdFimageForString);
		
		}else {
			logger.debug("PDF图片解析失败！");
		}
	}

	/**
	 * 功能描述：将图片解析为字符串
	 * @return
	 */
	public static String getPDFimageForString(List<PDImageXObject> list,String imageRootPath ,String pdfFileName,HttpServletRequest request){
		String substring = pdfFileName.substring(0,pdfFileName.length()-4);
		String imagePath = ParsePDFFileUtile.getImageByte(list, imageRootPath, substring);
		StringBuilder sb = new StringBuilder();
		File imageFile= new File(imagePath);
		String[] imageList = imageFile.list();
		ITesseract instance = new Tesseract();
		instance.setDatapath("D:\\jeesite\\work\\jeesite-master\\tessdata");
		instance.setLanguage("chi_sim");
		ImageIO.scanForPlugins();
		if(imageList.length>0){
			for (String image : imageList) {
				//如果未将tessdata放在根目录下需要指定绝对路径
				//instance.setDatapath(request.getSession().getServletContext().getRealPath("/tessdata"));
				//如果需要识别英文之外的语种，需要指定识别语种，并且需要将对应的语言包放进项目中
				// 指定识别图片
				File imgDir = new File(imageRootPath+"\\"+substring+"\\"+image);
				try {
					String ocrResult = instance.doOCR(imgDir);
					sb.append(ocrResult);
				} catch (TesseractException e) {
					logger.debug("图片解析为字符串[失败]",e);
					break;
				}
			}
			return sb.toString();
		}else {
			return PDF_FILE_CODE_BLANK;
		}

	}

	/**
	 * 功能描述：pdf中的图片放入到指定文件夹下
	 * @param list 装载PDImageXObject对象的集合
	 * @param imageRootPath 存放图片的根目录
	 * @param pdfFileName pdf文件名称
	 */
	public static String getImageByte(List<PDImageXObject> list,String imageRootPath ,String pdfFileName){
		if(list!=null && list.size()>0){
			boolean createDirectory = FileUtils.createDirectory(imageRootPath+pdfFileName+"\\");
			for (int i = 0; i < list.size(); i++) {
				PDImageXObject pdImageXObject = list.get(i);
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
				if(createDirectory){
					FileOutputStream fout =null;
					ByteArrayInputStream byteArrayInputStream=null;
					try {
						String suffix = pdImageXObject.getSuffix();
						if(suffix=="jb2"){
							suffix="jpg";
						}
						fout = new FileOutputStream(imageRootPath+pdfFileName+"\\"+simpleDateFormat.format(new Date())+"."+suffix);
						BufferedImage image = pdImageXObject.getImage();
						ByteArrayOutputStream os = new ByteArrayOutputStream();
						ImageIO.write(image, suffix,os);
						byteArrayInputStream = new ByteArrayInputStream(os.toByteArray());
						int byteCount = 0;
						byte[] bytes = new byte[1024];

						while ((byteCount = byteArrayInputStream.read(bytes)) > 0){
							fout.write(bytes,0,byteCount);
						}

					} catch (IOException e) {
						logger.debug("IO异常",e);
					}finally {
						try {
							if(null!=fout){
								fout.close();
							}
							if(null!=byteArrayInputStream){
								byteArrayInputStream.close();

							}
						} catch (IOException e) {
							logger.debug("IO异常",e);
						}

					}
				}
			}

		}else {
			logger.debug("图片文件列表为空");
		}
		return imageRootPath+"\\"+pdfFileName+"\\";
	}


	/**
	     * 从pdf文档中读取所有的图片信息
	     * 
	     * @return
	     * @throws Exception 
	     */
	public static List<PDImageXObject> getImageListFromPDF(PDDocument document,Integer startPage) {
		List<PDImageXObject> imageList = new ArrayList<PDImageXObject>();
		if(null != document){
			PDPageTree pages = document.getPages();
			startPage = startPage == null ? 0 : startPage;
			int len = pages.getCount();
			if(startPage < len){
				for(int i=startPage;i<len;i++){
					PDPage page = pages.get(i);
					Iterable<COSName> objectNames = page.getResources().getXObjectNames();
					for(COSName imageObjectName : objectNames){
						if(page.getResources().isImageXObject(imageObjectName)){
							try {
								imageList.add((PDImageXObject) page.getResources().getXObject(imageObjectName));
							} catch (IOException e) {
								logger.debug("获取图片错误",e);
							}
						}
					}
				}
			}	
		}
		return imageList;
	}



	/**
	 * 
	 *功能描述：中国注册会计师1
	 * @param pdfContent pdf文件解析后的内容
	 * @param pdfName pdf 文件名称
	 * @param pdfSrc 存在问题的、待复制的pdf文件
	 * @param chineseName 通过查询到的中文名称去匹配标题
	 * @return
	 */
	public static Set<String> getChinese_CPA(String pdfContent){
		String[] regexs={"注册会计师姓名.*.","中国注册会计师：.*.","中国注册会计师.*."};
		Set<String> set = new HashSet<String>();
		OUT:
			for (String regex : regexs) {
				Set<String> valueToRegex = ParsePDFFileUtile.getValueToRegex(pdfContent, regex);
				if(valueToRegex.size()>0){
					for (String item : valueToRegex) {
						if(item.contains("注册会计师")){
							if(item.contains("、")){
								String[] split = item.split("、");
								String string = split[0];
								if(string.contains("姓名")){
									int indexOf = string.indexOf("姓名");
									String substring = string.substring(indexOf+2, string.length());
									set.add(substring);
									String string1= split[1];
									set.add(string1.trim());
								}else {
									break OUT;
								}

								if(split.length==3){
									set.add(split[2].trim());
									break OUT;
								}else{

									break OUT;
								}

							}else if (item.contains(" ")) {
								String replace = item.replace(" ", "");
								String[] split = replace.split(",");
								if(item.contains("姓名")){
									int indexOf = item.indexOf("姓名");
									String substring = item.substring(indexOf+2, item.length());
									if(substring.contains(" ")){


										String replaceFirst = substring.trim().replaceFirst("  ", "");
										String[] split2 = replaceFirst.split(" ");
										for (String string : split2) {
											if(!string.equals("")&&string!=""){
												set.add(string);
											}else {
												continue;
											}
										}
										break OUT;

									}else if (item.contains("，")) {
										String[] split1 = item.split(" ");

										for (String string : split1) {
											if(!string.equals("")&&string!=""){
												set.add(string);
											}else {
												continue;
											}
										}
										break OUT;
									}
								}
								if(split.length==3){
									String string = split[1];
									set.add(string);
									String string1= split[2];
									set.add(string1.trim());
									break OUT;

								}else if (split.length==1) {
									break OUT;
								}
								else if (split.length==3) {
									String string = split[1];
									set.add(string);
									String string1= split[2];
									String string2= split[3];
									if(string1.length()>0 && string1!=" "){
										set.add(string1.trim());
									}else if(string2.length()>0 && string2!=" "){
										set.add(string2.trim());
										//									set.clear();
										break OUT;
									}
									break OUT;
								}
								else if (split.length==4) {
									String string = split[1];
									set.add(string);
									String string1= split[2];
									String string2= split[3];
									if(string1.length()>0 && string1!=" "){
										set.add(string1.trim());
									}else if(string2.length()>0 && string2!=" "){
										set.add(string2.trim());
										//									set.clear();
										break OUT;
									}
									break OUT;
								}
								else if(item.contains("，")&&item.contains(" ")){
									String[] split1 = item.split(" ");
									String[] split2 = split1[1].split("，");
									set.add(split2[0]);
									set.add(split2[1]);

									break OUT;

								}
							}else {
								break;
							}
						}else if(item.contains("中国注册会计师：")||item.contains("中国注册会计师")){
							set.add(item);
							break;
						}else {
							break;
						}
					}

				}else {
					continue;
				}
			}
		return set;
	}


	public static Map<Integer,String> getChinese_CPA_Regex(String subPdfContent,String regex){
		//Set<String> set = new HashSet<String>();
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(subPdfContent);
		Map<Integer,String> map= new HashMap<Integer,String>();
		while (m.find( )) {
			//			System.out.println(m.group());
			int indexOf = subPdfContent.indexOf(m.group());
			//			System.out.println(indexOf);
			//			set.add(m.group());
			map.put(indexOf, m.group());
		} 
		m.reset();
		return map;		
	}

	/**
	 * 
	 *功能描述：中国注册会计师2
	 * @param pdfContent pdf文件解析后的内容
	 * @param pdfName pdf 文件名称
	 * @param pdfSrc 存在问题的、待复制的pdf文件
	 * @param chineseName 通过查询到的中文名称去匹配标题
	 * @return
	 */
	public static String getChinese_CPA_2(String pdfContent,String pdfName,String pdfSrc,String chineseName){
		return chineseName;
	}

	/**
	 * 
	 *功能描述：公司董事、监事、高管简介
	 * @param pdfDocument 按照指定开始页查询到的pdf文档
	 * @param pdfContent pdf文件解析后的内容
	 * @param pdfName pdf 文件名称
	 * @param pdfSrc 存在问题的、待复制的pdf文件
	 * @param chineseName 通过查询到的中文名称去匹配标题
	 * @return
	 */
	public static String getDirector_supervisor_topManager_introInfo(PDDocument pdfDocument,String pdfContent){
		String numParseChinesenum=null;
		String mangerIntro = null;
		String regexStart= ".*.董事、监事、.*.情况.*.";
		//pdf文档匹配正则获取的字符串（文档目录） managerStr
		List<String> valueFromRegex = ParsePDFFileUtile.getValueToRegex1(pdfContent, regexStart);
		String managerStr=null;
		if(valueFromRegex.size()>0){
			managerStr = valueFromRegex.iterator().next();

		}else {
			return ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL;
		}

		List<String> chineseStrOfnum = ParsePDFFileUtile.getChineseStrOfnum();
		//判断managerStr中是否包含阿拉伯数字（目录所在的页数）
		if(managerStr!=null){
			Map<String, String> valueFromRegex3 = ParsePDFFileUtile.getMangerTopRegex(managerStr, "\\d");
			String num = valueFromRegex3.get("msg");
			if(num!=null){
				//定位到 managerStr 所在pdf文档中的页数起始位置、并从起始位置到pdf文档结束页的这一段内容
				String pdfContentPageNum = ParsePDFFileUtile.getPDFContentPageNum(pdfDocument,Integer.parseInt(num));
				//在 pdfContentPageNum 这一段文档中再次进行正则匹配
				List<String> managerList = ParsePDFFileUtile.getValueToRegex1(pdfContentPageNum, regexStart);
				//得到 manager （在文档中的起始位置）
				if(managerList.size()>0){
					String manager = managerList.get(0);
					if(manager!=null && !manager.equals("")){
						OUT:
							//遍历中文数字字符 进行转换匹配
							for (String item : chineseStrOfnum) {
								if(managerStr.contains(item)){
									int chinesenumParseNum = ParsePDFFileUtile.chinesenumParseNum(item);
									if(chinesenumParseNum==0){
										logger.debug("["+chinesenumParseNum+"]不是中文的数字");
									}else if(chinesenumParseNum==400){
										logger.debug("["+chinesenumParseNum+"]传入的数字为空");
									}else {
										numParseChinesenum = numParseChinesenum(chinesenumParseNum+1);
										if(numParseChinesenum!=null){
											String regexEnd0= "第"+numParseChinesenum+"节";
											String regexEnd1= "第"+numParseChinesenum+"章";
											String regexEnd2= numParseChinesenum+"、";
											String [] regexEnds = {regexEnd0,regexEnd1,regexEnd2};
											int indexOfStart = pdfContentPageNum.indexOf(manager);
											String subPDFContent = pdfContentPageNum.substring(indexOfStart, pdfContentPageNum.length());
											for (String regexEnd : regexEnds) {
												Map<String, String> valueFromRegex2 = ParsePDFFileUtile.getValueFromRegex(subPDFContent,regexEnd);
												if(valueFromRegex2.get("msg")!=null){
													mangerIntro = subPDFContent.substring(0, subPDFContent.indexOf(valueFromRegex2.get("msg")));
													break OUT;

												}else {
													continue;
												}

											}

										}else{
											logger.debug("["+chinesenumParseNum+"]为空，不能够转换1~10以外的阿拉伯数字");
										}
									}
								}

							}
					}else {
						return ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL;
					}
				}else {
					return ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL;
				}
				return mangerIntro;

			}else {
				return ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL;
			}
		}else {
			return ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL;
		}


	}

	/**
	 * 功能描述：阿拉伯数字转中文的数字(1-10)
	 * @param chinesenum 中文数字
	 * @return
	 */
	private static String numParseChinesenum(int num){
		Map<Integer, String> map = new HashMap<Integer, String>();
		map.put(1,"一");
		map.put(2,"二");
		map.put(3,"三");
		map.put(4,"四");
		map.put(5,"五");
		map.put(6,"六");
		map.put(7,"七");
		map.put(8,"八");
		map.put(9,"九");
		map.put(10,"十");
		map.put(11,"十一");
		map.put(12,"十二");
		map.put(13,"十三");
		map.put(14,"十四");
		map.put(15,"十五");
		map.put(16,"十六");
		map.put(17,"十七");
		map.put(18,"十八");
		map.put(19,"十九");
		map.put(20,"二十");
		if(Integer.valueOf(num)!=null && num<=20){
			String numStr = map.get(num);
			if (numStr!=null && numStr!="") {

				return numStr;
			}else {
				return null;
			}

		}else {
			return null;
		}

	}

	/**
	 * 功能描述：中文的数字转阿拉伯数字(1-10)
	 * @param chinesenum 中文数字
	 * @return
	 */
	private static int chinesenumParseNum(String chinesenum){
		Map<String, String> map = new HashMap<String, String>();
		map.put("一", "1");
		map.put("二","2");
		map.put("三", "3");
		map.put("四", "4");
		map.put("五", "5");
		map.put("六", "6");
		map.put("七", "7");
		map.put("八", "8");
		map.put("九", "9");
		map.put("十", "10");
		map.put("十一", "11");
		map.put("十二", "12");
		map.put("十三", "13");
		map.put("十四", "14");
		map.put("十五", "15");
		map.put("十六", "16");
		map.put("十七", "17");
		map.put("十八", "18");
		map.put("十九", "19");
		map.put("二十", "20");
		map.put("error", "400");
		if(chinesenum!=null &&chinesenum!=""){
			int intValue =0;
			String numStr = map.get(chinesenum);
			if (numStr!=null && numStr!="") {
				intValue = Integer.parseInt(numStr);
				return intValue;
			}else {
				return 0;
			}
		}else {
			return Integer.getInteger(map.get("error")).intValue();
		}

	}

	/**
	 * pdf文件目录的序号集合
	 */
	private static List<String> getChineseStrOfnum() {
		List<String> list = new ArrayList<String>();
		list.add("一");
		list.add("二");
		list.add("三");
		list.add("四");
		list.add("五");
		list.add("六");
		list.add("七");
		list.add("八");
		list.add("九");
		list.add("十");

		return list;
	}

	/**
	 * 
	 *功能描述：主承销商1、主承销商2
	 * @param pdfContent pdf文件解析后的内容
	 * @param pdfName pdf 文件名称
	 * @param pdfSrc 存在问题的、待复制的pdf文件
	 * @param chineseName 通过查询到的中文名称去匹配标题
	 * @return
	 */
	public static String getLead_underwriterInfo(String pdfContent){
		String regex =".*.主承销商：.*.，.*.";
		Map<String, String> valueFromRegex = ParsePDFFileUtile.getValueFromRegex(pdfContent, regex);
		String leadUnderwriter = valueFromRegex.get("msg");
		if(leadUnderwriter!=null){
			return leadUnderwriter;
		}else {
			String regex1 =".*.主承销商：.*.";
			Map<String, String> valueFromRegex1 = ParsePDFFileUtile.getValueFromRegex(pdfContent, regex1);
			String leadUnderwriter1 = valueFromRegex1.get("msg");
			if(leadUnderwriter1!=null){
				return leadUnderwriter1;
			}else {
				return ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL;
			}

		}
	}

	/**
	 * 
	 *功能描述：资信评级机构
	 * @param pdfContent pdf文件解析后的内容
	 * @param pdfName pdf 文件名称
	 * @param pdfSrc 存在问题的、待复制的pdf文件
	 * @param chineseName 通过查询到的中文名称去匹配标题
	 * @return
	 */
	public static String getCredit_rating_agenciesInfo(String pdfContent){
		String regex = ".*.资信.*.公司.*.";
		Map<String, String> valueFromRegex = ParsePDFFileUtile.getValueFromRegex(pdfContent, regex);
		String item = valueFromRegex.get("msg");
		if(item!=null){
			return item;
		}
		else {
			return ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL;
		}

	}
	/**
	 * 
	 *功能描述：发布时间
	 * @param pdfContent pdf文件解析后的内容
	 * @param pdfName pdf 文件名称
	 * @param pdfSrc 存在问题的、待复制的pdf文件
	 * @param chineseName 通过查询到的中文名称去匹配标题
	 * @return
	 */
	public static String getRelease_time(String pdfContent,String pdfName,String pdfSrc,String chineseName){

		return chineseName;
	}
	/**
	 * 
	 *功能描述：聘用、解聘会计师事务所情况 、收费
	 * @param pdfContent pdf文件解析后的内容
	 * @param pdfName pdf 文件名称
	 * @param pdfSrc 存在问题的、待复制的pdf文件
	 * @param chineseName 通过查询到的中文名称去匹配标题
	 * @return
	 */
	public static String getEngage_dismiss_accounting_firmInfo(PDDocument pdfDocument,String pdfContent){
		String content =null;
		String regex= ".*.聘任、解聘会计师事务所情况.*.";
		//返回所有的匹配内容
		Set<String> itemList = ParsePDFFileUtile.getValueToRegex(pdfContent, regex);
		List<String> chineseStrOfnum = ParsePDFFileUtile.getChineseStrOfnum();
		//
		/**1.如果没有匹配内容的处理
		 * 2.如果返回只有一条匹配内容的处理
		 * 3.判断itemList如果在一条以上的处理 
		 */
		if(itemList.size()==0){
			return ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL;
		}else if (itemList.size()==1) {
			Stack<String> stack = new Stack<String>();
			String item = itemList.iterator().next();
			Map<String, String> valueFromRegex = ParsePDFFileUtile.getValueFromRegex(item, "\\d");
			String pageNum = valueFromRegex.get("msg");
			if(pageNum!=null){
				String pdfContentPageNum = ParsePDFFileUtile.getPDFContentPageNum(pdfDocument,Integer.parseInt(pageNum));
				Map<String, String> managerMap = ParsePDFFileUtile.getValueFromRegex(pdfContentPageNum, regex);
				String regexResult = managerMap.get("msg");
				if(regexResult!=null){
					String subPdfContent = pdfContentPageNum.substring(pdfContentPageNum.indexOf(item), pdfContentPageNum.length());
					content = ParsePDFFileUtile.getContent(content, chineseStrOfnum, stack, item, subPdfContent);
				}else {
					return ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL;
				}
			}else {
				//找到匹配到的关键字 的位置 截取开始位置到文档末尾
				String subPdfContent = pdfContent.substring(pdfContent.indexOf(item), pdfContent.length());
				content =ParsePDFFileUtile.getContent(content, chineseStrOfnum, stack, item, subPdfContent);
			}
		}
		else if (itemList.size()>1) {
			Stack<String> stack = new Stack<String>();
			String subPdfContent=null;
			//找到匹配到的关键字 的位置 截取开始位置到文档末尾
			for (String item : itemList) {
				Map<String, String> valueFromRegex = ParsePDFFileUtile.getValueFromRegex(item, "\\d");
				String pageNum = valueFromRegex.get("msg");
				if(pageNum!=null){
					//获取从起始页开始的内容
					String pdfContentPageNum = ParsePDFFileUtile.getPDFContentPageNum(pdfDocument,Integer.parseInt(pageNum));
					Map<String, String> managerMap = ParsePDFFileUtile.getValueFromRegex(pdfContentPageNum, regex);
					String regexResult = managerMap.get("msg");
					if(regexResult!=null){
						String subPdfContent1 = pdfContentPageNum.substring(pdfContentPageNum.indexOf(regexResult), pdfContentPageNum.length());
						content = ParsePDFFileUtile.getContent(content, chineseStrOfnum, stack, item, subPdfContent1);
						if(content!=null){
							break;
						}else {
							continue;
						}
					}else {
						return ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL;
					}
				}else {
					subPdfContent = pdfContent.substring(pdfContent.indexOf(item), pdfContent.length());
					content = ParsePDFFileUtile.getContent(content, chineseStrOfnum, stack, item, subPdfContent);
				}
			}
			if(content!=null){
				Iterator<String> iterator = stack.iterator();
				content = ParsePDFFileUtile.getFiledContent(stack, subPdfContent, iterator);
			}else {
				return ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL;
			}

		}
		else {
			return ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL;
		}


		return content;
	}
	/**
	 * 功能描述：截取属性字段的内容
	 * @param content
	 * @param chineseStrOfnum
	 * @param stack
	 * @param item
	 * @param subPdfContent
	 * @return
	 */
	private static String getContent(String content, List<String> chineseStrOfnum, Stack<String> stack, String item,
			String subPdfContent) {
		if(subPdfContent!=null){
			for (String num : chineseStrOfnum) {
				if(item.contains(num)){
					stack.push(num);

				}else {
					continue;
				}
			}
			Iterator<String> iterator = stack.iterator();
			content = ParsePDFFileUtile.getFiledContent(stack, subPdfContent, iterator);
		}
		else {
			return ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL;
		}
		return content;
	}

	/**
	 * 功能描述：截取属性字段的内容
	 * @param stack
	 * @param subPdfContent
	 * @param iterator
	 * @return
	 */
	private static String getFiledContent(Stack<String> stack, String subPdfContent, Iterator<String> iterator) {
		String content = null;
		if(stack.size()==1){
			String next = iterator.next();
			content = ParsePDFFileUtile.subPdfContent(subPdfContent, next);

		}else if (stack.size()==2) {
			StringBuilder sb = new StringBuilder();
			while (iterator.hasNext()) {
				//String next1 = iterator.next();
				String pop = stack.pop();
				sb.append(pop);
			}
			content = ParsePDFFileUtile.subPdfContent(subPdfContent, sb.toString());


		}
		return (content==null)?ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL:content;
	}

	/**
	 * 功能描述：截取匹配到的字段所处在解析后的pdf文件中的位置
	 * @param subPdfContent pdf开始位置到pdf文件的结束位置
	 * @param num 目录章节号
	 * @return
	 */
	private static String subPdfContent(String subPdfContent, String num) {
		//内容中的序号（中文数字）转阿拉伯数字 加1得到下一章节的位置
		int chinesenumParseNum = ParsePDFFileUtile.chinesenumParseNum(num)+1;
		//阿拉伯数字转中文
		String numParseChinesenum = ParsePDFFileUtile.numParseChinesenum(chinesenumParseNum);
		if(numParseChinesenum!=null && numParseChinesenum!=""){
			//截取文档的开始位置 到 出现的关键字的末尾
			String substring = subPdfContent.substring(0, subPdfContent.indexOf(numParseChinesenum));

			return substring;
		}else {
			return null;
		}
	}





	/********************************************************年报告中与半年报告中不同的字段方法===========结束************************************************************************	





	/**
	 * 
	 *功能描述：获取title
	 * @param pdfContent pdf文件解析后的内容
	 * @param pdfName pdf 文件名称
	 * @param pdfSrc 存在问题的、待复制的pdf文件
	 * @param chineseName 通过查询到的中文名称去匹配标题
	 * @return
	 */
	public static String getTitleInfo(String pdfContent,String chineseName){
		String regex=".*.年度报告";
		String title = null; 
		Map<String, String> valueFromRegex = ParsePDFFileUtile.getValueFromRegex(pdfContent, regex);
		String regTitle = valueFromRegex.get("msg");
		if(regTitle!=null){
			title= regTitle;
			if(title.contains(chineseName)){
				return title;
			}else {
				title = chineseName+title;
				if(title.contains(" ")){
					title = title.replaceAll(" ", "");
					return title;
				}else {
					return title;
				}
			}
		}else {
			return ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL;
		}

	}


	/**
	 * 
	 *功能描述：获取公司中文名称
	 * @param pdfContent pdf文件解析后的内容
	 * @param pdfName pdf 文件名称
	 * @param pdfSrc 存在问题的、待复制的pdf文件
	 * @param fileName
	 * @return
	 */
	public static String getChineseNameInfo(String pdfContent,String pdfName){
		String[] regexs={".*.中.*.称.*.公司","中.*.称.*.公司","公司.*.文全称.*."};
		System.out.println(pdfContent);
		String chinesename = null ; 
		for (String regex : regexs) {
			Map<String, String> chineseNameMap = ParsePDFFileUtile.getValueFromRegex(pdfContent, regex);
			String chineseName = chineseNameMap.get("msg");
			if(chineseName!=null){
				//String subchineseName = chineseName.substring(6,chineseName.length());

				//chinesename = subchineseName.trim();
				chinesename=chineseName;
				continue;
			}else {
				continue;
			}

		}
		if(chinesename!=null){

			return chinesename;
		}else {
			return ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL;

		}


	}

	/**
	 * 
	 *功能描述：获取公司英文名称
	 * @param pdfContent pdf文件解析后的内容
	 * @param pdfName pdf 文件名称
	 * @param pdfSrc 存在问题的、待复制的pdf文件
	 * @param chineseName已经获取到的中文名称
	 * @param fileName
	 * @return
	 */
	public static String getEnglishNameInfo(String pdfContent,String chineseName){
		String[] regexs={"英文名称及缩写.*.",".*.英文.*.","外文名称.*.",".*.外文名称.*."};
		String englishname=null;
		for (String regex : regexs) {
			Map<String, String> englishNameMap = ParsePDFFileUtile.getValueFromRegex(pdfContent, regex);
			String englishName = englishNameMap.get("msg");
			String ename=null;
			if(englishName!=null){
				int indexOfEnglishName = pdfContent.indexOf(chineseName);
				String subEnglishName = pdfContent.substring(indexOfEnglishName, pdfContent.length());
				if(englishName.length()>15){
					return englishName;
				}else if(englishName.length()<15){
					String[] regexEnd={"LIMITED","Limited","CO.,LTD","Co.,Ltd.","co.,ltd","Co., Ltd","Co., LTD","CO., LTD","Co., Ltd."};
					for (String regexItem : regexEnd) {
						String reg = regexItem;
						Map<String, String> valueFromRegex = ParsePDFFileUtile.getValueFromRegex(subEnglishName, reg);
						String englishName1 = valueFromRegex.get("msg");
						if(englishName1!=null&&!englishName1.equals("")){
							int start = subEnglishName.indexOf(chineseName);
							int end = subEnglishName.indexOf(regexItem);

							if(end>start){
								ename = subEnglishName.substring(start, end+9);
							}else {
								continue;
							}
						}else {
							ename= ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL;
						}
					}


					englishname= ename;
				}
				else {
					continue;
				}
			}else {
				continue;

			}
		}
		if(englishname!=null){
			return englishname;
		}else {

			return ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL;
		}
	}

	/**
	 * 
	 *功能描述：公司聘用的会计师事务所
	 * @param pdfContent pdf文件解析后的内容
	 * @param pdfName pdf 文件名称
	 * @param pdfSrc pdf
	 * @param fileName
	 * @return
	 */
	public static String getAccountingFirmInfo(String pdfContent) {
		//定义匹配不到字段所属的文件存放的位置
		String accountingFirmName=null;
		//公司聘用的会计师事务所****
		if(pdfContent.contains("公司聘用的会计师事务所")){
			String regex8 = "[^：|\t]*.*[会计师事务所]";
			Map<String, String> accounting_firm = ParsePDFFileUtile.getValueFromRegex(pdfContent, regex8);
			accountingFirmName = accounting_firm.get("msg");
		}else if(pdfContent.contains("会计师事务所")){
			String regex7 = "会计师事务所*.*会计师事务所";
			Map<String, String> valueFromRegex = ParsePDFFileUtile.getValueFromRegex(pdfContent, regex7);

			if(valueFromRegex.get("msg")!=null){
				accountingFirmName=valueFromRegex.get("msg");
				//	System.out.println("公司聘用的会计师事务所=>"+accountingFirmName);
			}else {
				String regex8 = "会计师事务所*.*";
				Set<String> set = ParsePDFFileUtile.getValueToRegex(pdfContent, regex8);
				for (String item : set) {
					if(item.contains("会计师事务所 无") || item.contains("会计师事务所 不适用")||item.contains("会计师事务所 -")||item.contains("会计师事务所（如有）")
							||item.contains("会计师事务所 ")||item.contains("会计师事务所 — ")){
						accountingFirmName=item;
						//	System.out.println("公司聘用的会计师事务所=>"+accountingFirmName);
						break;
					}

					else {

						String regex9 = ".*.会计师事务所";
						Map<String, String> valueFromRegex1 = ParsePDFFileUtile.getValueFromRegex(pdfContent, regex9);
						if(valueFromRegex1.get("msg")!=null){
							accountingFirmName=valueFromRegex1.get("msg");
							//	System.out.println("公司聘用的会计师事务所=>"+accountingFirmName);
							break;
						}
					}
				}
			}
		}
		else {
			//logger.debug("【"+pdfName+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.ACCOUNTING_FIRM_OF_COMPANY);
			//处理匹配不到的字段所属文件的方法
			return ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL;
		}
		return accountingFirmName;
	}




	/**
	 * 
	 * @param pdfSrc 待复制的有问题的pdf文件
	 * @param filedToPath 目标文件存放目录
	 * @param fileName 目标文件
	 * @return
	 */
	public static void errorFiledPross(String pdfSrc,String filedToPath,String fileName) {
		File file = new File(pdfSrc);
		if(file.exists()){
			if(FileUtils.copyFileCover(pdfSrc, filedToPath+fileName, true)){

				logger.debug("【问题文件】"+filedToPath+fileName+"------复制成功");

			}else {
				logger.debug("【问题文件】"+filedToPath+fileName+"------复制失败");
			}
		}else {
			FileUtils.createDirectory(filedToPath+fileName);

			if(FileUtils.copyFileCover(pdfSrc, filedToPath+fileName, true)){
				logger.debug("【问题文件】"+filedToPath+fileName+"-------复制成功");

			}else {
				logger.debug("【问题文件】"+filedToPath+fileName+"------复制失败");
			}
		}
	}




	/**
	 * 功能描述：主管会计工作负责人的字段信息
	 * @param pdfContent pdf文件解析后的内容
	 * @return 
	 */
	public static String getAccountingDirectorInfo(String pdfContent) {
		//主管会计工作负责人
		String ad = null;
		String[] regexs = {"主管会计工作负责人：.*.","主管会计机构负责人：","主管会计工作的负责人：.*."};
		for (String regex : regexs) {
			Map<String, String> accountingBirector = ParsePDFFileUtile.getValueFromRegex(pdfContent, regex);
			String accountingBirectorName = accountingBirector.get("msg");
			if(accountingBirectorName!=null){
				ad = accountingBirectorName;
			}
			else {
				continue;
			}

		}
		if(ad!=null){
			return ad;
		}else {
			return ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL;
		}
	}







	/**
	 * 功能描述：获取会计机构负责人的字段信息
	 * @param pdfContent pdf文件解析后的内容
	 * @return
	 */
	public static String getOrganizationAccountingDirectorInfo(String pdfContent) {
		String[] regexs = {"会计机构负责人：.*."};
		String oad = null;
		for (String regex : regexs) {
			Map<String, String> organizationAccountingDirector = ParsePDFFileUtile.getValueFromRegex(pdfContent, regex);
			String organizationAccountingDirectorName = organizationAccountingDirector.get("msg");
			if(organizationAccountingDirectorName!=null){

				oad= organizationAccountingDirectorName;
				continue;
			}else {
				continue;
			}

		}
		if(oad!=null){
			return oad;

		}else {

			return ParsePDFFileUtile.PDF_FILE_CODE_FILED_NULL;
		}
	}



	/**
	 * 功能描述：获取公司持股人的字段信息
	 * @param pdfContent pdf文件解析后的内容
	 * @param pdfName pdf 文件名称
	 * @param pdfName pdfSrc 文件名称
	 * @return
	 */
	public static String getShareholderOfCompanyInfo(String pdfContent,String pdfName,String pdfSrc) {
		//开始截取
		String regex7 = ".*.前(前?五|十).*.+(股东情况)";
		//结束截取
		String regex8=".*.间相互关系说明：";
		String shareHolderOfCompany = null;
		Map<String, String> startStrMap = ParsePDFFileUtile.getValueFromRegex(pdfContent, regex7);
		Map<String, String> endStrMap = ParsePDFFileUtile.getValueFromRegex(pdfContent, regex8);
		String startStr = startStrMap.get("msg");
		String endStr = endStrMap.get("msg");
		if(startStr!=null || startStr!=""){
			int startStrIndexOf = pdfContent.indexOf(startStr);
			int endStrIndexOf = pdfContent.indexOf(endStr);
			shareHolderOfCompany = pdfContent.substring(startStrIndexOf, endStrIndexOf);
		}else {
			logger.debug("【"+pdfName+"】文件中不包含===============[属性：]"+ParsePDFFileUtile.SHAREHOLDER_OF_COMPANY);
			errorFiledPross(pdfSrc,ParsePDFFileUtile.FILED_TO_PATH+ParsePDFFileUtile.SHAREHOLDER_OF_COMPANY+"\\",ParsePDFFileUtile.SHAREHOLDER_OF_COMPANY);
		}

		return shareHolderOfCompany;
	}




	/**
	 * 
	 * @param path 文件读取目录
	 * @param outFile 文件输出目录
	 */
	public static Map<String, Integer> readText(String path,String outFile ){
		Map<String, Integer> map = new HashMap<String, Integer>();
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null; //用于包装InputStreamReader,提高处理性能。因为BufferedReader有缓冲的，而InputStreamReader没有。
		FileOutputStream bw = null;
		try {

			String str = "";
			fis = new FileInputStream(path);// FileInputStream
			bw = new FileOutputStream(outFile);
			// 从文件系统中的某个文件中获取字节
			isr = new InputStreamReader(fis);// InputStreamReader 是字节流通向字符流的桥梁,
			br = new BufferedReader(isr);// 从字符输入流中读取文件中的内容,封装了一个new InputStreamReader的对象

			while ((str = br.readLine()) != null) {
				bw.write(str.getBytes());
			}
			map.put("msg", 200);
			System.out.println();
		} catch (Exception e) {
			System.out.println("找不到指定文件");
		}  finally {
			try {
				br.close();
				br.close();
				isr.close();
				fis.close();
				// 关闭的时候最好按照先后顺序关闭最后开的先关闭所以先关s,再关n,最后关m
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return map;
	}




	/**
	 * 将pdf 文件名称截取   截取()里面的内容 [定期报告]ST百拓-(2017年半年度报告).pdf
	 * @param pdfFileName pdf 文件名称
	 * @return
	 */
	public static Map<String, String> subStr(String pdfFileName){
		Map<String, String> map = new HashMap<String, String>();
		if(pdfFileName!=null && pdfFileName !="" && pdfFileName.contains("-")){
			int indexOf = pdfFileName.indexOf("-");
			String title = pdfFileName.substring(indexOf+1, pdfFileName.length()-4);
			map.put("msg", title);
			return map;

		}else if(pdfFileName.contains("：")){
			int indexOf = pdfFileName.indexOf("：");
			String title = pdfFileName.substring(indexOf+1, pdfFileName.length()-4);
			map.put("msg", title);
			return map;
		}
		else {
			map.put("msg", null);
			return map;
		}
	}




	/**
	 * 
	 * @param titleStr 标题
	 * @param str 以什么字符串进行截取
	 * @return
	 */
	public static Map<String, String> subStr(String titleStr,String str){
		Map<String, String> map = new HashMap<String, String>();
		if(titleStr!=null && titleStr !=""){
			int indexOf = titleStr.indexOf(str);
			String title = titleStr.substring(indexOf+1, titleStr.length());
			map.put("msg", title);
			return map;

		}else {
			map.put("msg", null);
			return map;
		}

	}




	/**
	 * 将字符串写入到text文件中
	 * @param filePath text文件路径
	 */
	@SuppressWarnings("resource")
	public static void WriteStringToFile(String filePath,String content) {
		try {
			File file = new File(filePath);
			PrintStream ps = new PrintStream(new FileOutputStream(file));
			ps.println(content);// 往文件里写入字符串
		} catch (FileNotFoundException e) {
			logger.debug("内容写入失败", e);        }
	}


	/**
	 * 
	 * @param content 文字内容
	 * @param regx 正则匹配规则
	 * @return
	 */
	public static Map<String, String> getMangerTopRegex(String content,String regex){
		Map<String, String> msgMap = new HashMap<String, String>();
		Pattern pattern = Pattern.compile(regex);
		LinkedList<String> linkedList = new LinkedList<String>();
		Matcher m = pattern.matcher(content);
		while (m.find( )) {
			linkedList.add(m.group());

		} 
		if(linkedList.size()>1){
			StringBuilder val = new StringBuilder();
			Iterator<String> iterator = linkedList.iterator();
			while (iterator.hasNext()) {
				String string = (String) iterator.next();
				val.append(string);
			}
			msgMap.put("msg", val.toString());
		}else {
			Iterator<String> iterator = linkedList.iterator();
			if(iterator.hasNext()){
				String next = iterator.next();
				msgMap.put("msg", next);
			}else {
				msgMap.put("msg", null);
			}

		}
		m.reset();
		return msgMap;		
	}


	/**
	 * 
	 * @param content 文字内容
	 * @param regx 正则匹配规则
	 * @return
	 */
	public static Map<String, String> getValueFromRegex(String content,String regex){
		Map<String, String> msgMap = new HashMap<String, String>();
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(content);
		if (m.find( )) {
			msgMap.put("msg", m.group()); 
			System.out.println(m.group(0));
			//logger.debug(name+"===>匹配成功!");
		} else {
			//logger.debug("未匹配到的【文件属性】:"+name+"   文件路径==>"+path);
			msgMap.put("msg", null); 
		}
		m.reset();
		return msgMap;		
	}


	public static Set<String> getValueToRegex(String content,String regex){
		Set<String> set = new HashSet<String>();
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(content);
		while (m.find( )) {
			set.add(m.group());
		} 
		m.reset();
		return set;		
	}

	public static List<String> getValueToRegex1(String content,String regex){
		LinkedList<String>  list = new LinkedList<String>();
		Pattern pattern = Pattern.compile(regex);
		Matcher m = pattern.matcher(content);
		while (m.find( )) {
			list.add(m.group());
		} 
		m.reset();
		return list;		
	}




	/**
	 * 获取pdf文件列表
	 * @param path 存放pdf文件的文件夹
	 * @return
	 */
	public static String[] getPDFList(String path){
		File file = new File(path);
		if(file.exists()){
			String[] list = file.list();
			if(list!=null && list.length>0  ){

				return list;
			}else {
				return null;
			}
		}
		return null;

	}








	/**
	 * 功能描述：获取指定页数的pdf文档
	 * @param document PDDocument对象
	 * @param fileSrc pdf文件所在的目录
	 * @param fileName pdf文件名称
	 * @return
	 */
	public static String getPDFContentPageNum(PDDocument document,int startPage){

		// 获取页码
		int pages = document.getNumberOfPages();

		// 读文本内容
		PDFTextStripper stripper=null;

		//获取pdf文件的内容
		String content=null;
		try {
			stripper = new PDFTextStripper();
			// 设置按顺序输出
			stripper.setSortByPosition(true);
			stripper.setStartPage(startPage);
			stripper.setEndPage(pages);
			content = stripper.getText(document);

		} catch (Exception e) {
			logger.debug("文件解析失败",e);
			return ParsePDFFileUtile.PDF_FILE_CODE_FAIL;

		}
		return content;

	}




	/**
	 * 
	 * @param path PDF的文件路径
	 * @param fileSrc pdf文件所在的目录
	 * @param fileName  pdf文件名称
	 * @return PDDocument对象
	 */
	public static Map<String, Object> getPDDocument(String path,String fileSrc,String fileName){
		Map<String, Object> map = new HashMap<String, Object>();
		if(path.endsWith(".pdf")){
			File pdfFile = new File(path);
			PDDocument document = null;
			try {
				byte[] readFileToByteArray = FileUtils.readFileToByteArray(pdfFile);
				if(readFileToByteArray.length>0){
					document=PDDocument.load(readFileToByteArray);
					map.put("msg", document);

				}else {
					map.put("msg", PDF_FILE_CODE_FAIL);

				}
			} catch (Exception e) {
				map.put("msg", PDF_FILE_CODE_FAIL);

			} 
			return map;
		}else{
			logger.debug("此文件类型不是【pdf格式】====>"+fileName);
			map.put("msg", PDF_FILE_CODE_FAIL);
			return map;
		}
	}

	/**
	 * 
	 * @param document PDDocument对象
	 * @param fileSrc pdf文件所在的目录
	 * @param fileName pdf文件名称
	 * @return
	 */
	public static String getPDFContent(PDDocument document){


		//获取pdf文件的内容
		String content=null;
		try {
			// 获取页码
			int pages = document.getNumberOfPages();

			// 读文本内容
			PDFTextStripper stripper=null;
			stripper = new PDFTextStripper();
			// 设置按顺序输出
			stripper.setSortByPosition(true);
			stripper.setStartPage(1);
			stripper.setEndPage(pages);
			content = stripper.getText(document);
		} catch (Exception e) {
			logger.debug("文件解析失败",e);
			return ParsePDFFileUtile.PDF_FILE_CODE_FAIL;

		}
		return content;

	}

}
