/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.classes.web;

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
import com.thinkgem.jeesite.modules.classes.entity.DemoClasses;
import com.thinkgem.jeesite.modules.classes.service.DemoClassesService;

/**
 * 班级信息Controller
 * @author zhb
 * @version 2018-11-28
 */
@Controller
@RequestMapping(value = "${adminPath}/classes/demoClasses")
public class DemoClassesController extends BaseController {

	@Autowired
	private DemoClassesService demoClassesService;
	
	@ModelAttribute
	public DemoClasses get(@RequestParam(required=false) String id) {
		DemoClasses entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = demoClassesService.get(id);
		}
		if (entity == null){
			entity = new DemoClasses();
		}
		return entity;
	}
	
	@RequiresPermissions("classes:demoClasses:view")
	@RequestMapping(value = {"list", ""})
	public String list(DemoClasses demoClasses, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DemoClasses> page = demoClassesService.findPage(new Page<DemoClasses>(request, response), demoClasses); 
		model.addAttribute("page", page);
		return "modules/classes/demoClassesList";
	}

	@RequiresPermissions("classes:demoClasses:view")
	@RequestMapping(value = "form")
	public String form(DemoClasses demoClasses, Model model) {
		model.addAttribute("demoClasses", demoClasses);
		return "modules/classes/demoClassesForm";
	}

	@RequiresPermissions("classes:demoClasses:edit")
	@RequestMapping(value = "save")
	public String save(DemoClasses demoClasses, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, demoClasses)){
			return form(demoClasses, model);
		}
		demoClassesService.save(demoClasses);
		addMessage(redirectAttributes, "保存班级成功");
		return "redirect:"+Global.getAdminPath()+"/classes/demoClasses/?repage";
	}
	
	@RequiresPermissions("classes:demoClasses:edit")
	@RequestMapping(value = "delete")
	public String delete(DemoClasses demoClasses, RedirectAttributes redirectAttributes) {
		demoClassesService.delete(demoClasses);
		addMessage(redirectAttributes, "删除班级成功");
		return "redirect:"+Global.getAdminPath()+"/classes/demoClasses/?repage";
	}

}