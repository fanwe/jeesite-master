/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.error.web.kuaijishishiwusuo;

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
import com.thinkgem.jeesite.modules.error.entity.kuaijishishiwusuo.Errorfile;
import com.thinkgem.jeesite.modules.error.service.kuaijishishiwusuo.ErrorfileService;

/**
 * 公司聘用的会计师事务所(未匹配到的)Controller
 * @author zhb
 * @version 2018-12-11
 */
@Controller
@RequestMapping(value = "${adminPath}/error/kuaijishishiwusuo/errorfile")
public class ErrorfileController extends BaseController {

	@Autowired
	private ErrorfileService errorfileService;
	
	@ModelAttribute
	public Errorfile get(@RequestParam(required=false) String id) {
		Errorfile entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = errorfileService.get(id);
		}
		if (entity == null){
			entity = new Errorfile();
		}
		return entity;
	}
	
	@RequiresPermissions("error:kuaijishishiwusuo:errorfile:view")
	@RequestMapping(value = {"list", ""})
	public String list(Errorfile errorfile, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Errorfile> page = errorfileService.findPage(new Page<Errorfile>(request, response), errorfile); 
		model.addAttribute("page", page);
		return "modules/error/kuaijishishiwusuo/errorfileList";
	}

	@RequiresPermissions("error:kuaijishishiwusuo:errorfile:view")
	@RequestMapping(value = "form")
	public String form(Errorfile errorfile, Model model) {
		model.addAttribute("errorfile", errorfile);
		return "modules/error/kuaijishishiwusuo/errorfileForm";
	}

	@RequiresPermissions("error:kuaijishishiwusuo:errorfile:edit")
	@RequestMapping(value = "save")
	public String save(Errorfile errorfile, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, errorfile)){
			return form(errorfile, model);
		}
		errorfileService.save(errorfile);
		addMessage(redirectAttributes, "保存会计师事务所成功");
		return "redirect:"+Global.getAdminPath()+"/error/kuaijishishiwusuo/errorfile/?repage";
	}
	
	@RequiresPermissions("error:kuaijishishiwusuo:errorfile:edit")
	@RequestMapping(value = "delete")
	public String delete(Errorfile errorfile, RedirectAttributes redirectAttributes) {
		errorfileService.delete(errorfile);
		addMessage(redirectAttributes, "删除会计师事务所成功");
		return "redirect:"+Global.getAdminPath()+"/error/kuaijishishiwusuo/errorfile/?repage";
	}

}