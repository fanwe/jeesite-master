/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.student.web;

import java.util.List;

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
import com.thinkgem.jeesite.modules.student.entity.DemoStudent;
import com.thinkgem.jeesite.modules.student.service.DemoStudentService;

/**
 * 学生表Controller
 * @author zhb
 * @version 2018-11-28
 */
@Controller
@RequestMapping(value = "${adminPath}/student/demoStudent")
public class DemoStudentController extends BaseController {

	@Autowired
	private DemoStudentService demoStudentService;
	@Autowired
	private DemoClassesService demoClassesService;
	@ModelAttribute
	public DemoStudent get(@RequestParam(required=false) String id) {
		DemoStudent entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = demoStudentService.get(id);
		}
		if (entity == null){
			entity = new DemoStudent();
		}
		return entity;
	}
	
	@RequiresPermissions("student:demoStudent:view")
	@RequestMapping(value = {"list", ""})
	public String list(DemoStudent demoStudent, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DemoStudent> page = demoStudentService.findPage(new Page<DemoStudent>(request, response), demoStudent); 
		model.addAttribute("page", page);
		return "modules/student/demoStudentList";
	}

	@RequiresPermissions("student:demoStudent:view")
	@RequestMapping(value = "form")
	public String form(DemoStudent demoStudent, Model model) {
		List<DemoClasses> classes = demoClassesService.findList(new DemoClasses());
		model.addAttribute("classes",classes);
		model.addAttribute("demoStudent", demoStudent);
		return "modules/student/demoStudentForm";
	}

	@RequiresPermissions("student:demoStudent:edit")
	@RequestMapping(value = "save")
	public String save(DemoStudent demoStudent, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, demoStudent)){
			return form(demoStudent, model);
		}
		demoStudentService.save(demoStudent);
		addMessage(redirectAttributes, "保存学生成功");
		return "redirect:"+Global.getAdminPath()+"/student/demoStudent/?repage";
	}
	
	@RequiresPermissions("student:demoStudent:edit")
	@RequestMapping(value = "delete")
	public String delete(DemoStudent demoStudent, RedirectAttributes redirectAttributes) {
		demoStudentService.delete(demoStudent);
		addMessage(redirectAttributes, "删除学生成功");
		return "redirect:"+Global.getAdminPath()+"/student/demoStudent/?repage";
	}

}