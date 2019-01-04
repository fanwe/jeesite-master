/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shareholdersinformation.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.shareholdersinformation.entity.ShareholdersInformation;
import com.thinkgem.jeesite.modules.shareholdersinformation.service.ShareholdersInformationService;

/**
 * 公司持股人情况Controller
 * @author zhb
 * @version 2018-12-10
 */
@Controller
@RequestMapping(value = "${adminPath}/shareholdersinformation/shareholdersInformation")
public class ShareholdersInformationController extends BaseController {

	@Autowired
	private ShareholdersInformationService shareholdersInformationService;
	
	@ModelAttribute
	public ShareholdersInformation get(@RequestParam(required=false) String id) {
		ShareholdersInformation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shareholdersInformationService.get(id);
		}
		if (entity == null){
			entity = new ShareholdersInformation();
		}
		return entity;
	}
	
	@RequiresPermissions("shareholdersinformation:shareholdersInformation:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShareholdersInformation shareholdersInformation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ShareholdersInformation> page = shareholdersInformationService.findPage(new Page<ShareholdersInformation>(request, response), shareholdersInformation); 
		model.addAttribute("page", page);
		return "modules/shareholdersinformation/shareholdersInformationList";
	}

	@RequiresPermissions("shareholdersinformation:shareholdersInformation:view")
	@RequestMapping(value = "form")
	public String form(ShareholdersInformation shareholdersInformation, Model model) {
		model.addAttribute("shareholdersInformation", shareholdersInformation);
		return "modules/shareholdersinformation/shareholdersInformationForm";
	}

	@RequiresPermissions("shareholdersinformation:shareholdersInformation:edit")
	@RequestMapping(value = "save")
	public String save(ShareholdersInformation shareholdersInformation, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shareholdersInformation)){
			return form(shareholdersInformation, model);
		}
		shareholdersInformationService.save(shareholdersInformation);
		addMessage(redirectAttributes, "保存公司持股人情况成功");
		return "redirect:"+Global.getAdminPath()+"/shareholdersinformation/shareholdersInformation/?repage";
	}
	
	@RequiresPermissions("shareholdersinformation:shareholdersInformation:edit")
	@RequestMapping(value = "delete")
	public String delete(ShareholdersInformation shareholdersInformation, RedirectAttributes redirectAttributes) {
		shareholdersInformationService.delete(shareholdersInformation);
		addMessage(redirectAttributes, "删除公司持股人情况成功");
		return "redirect:"+Global.getAdminPath()+"/shareholdersinformation/shareholdersInformation/?repage";
	}

}