/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shopwarehouse.web;

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
import com.thinkgem.jeesite.modules.shopwarehouse.entity.ShopWarehose;
import com.thinkgem.jeesite.modules.shopwarehouse.service.ShopWarehoseService;

/**
 * 仓库列表Controller
 * @author zhb
 * @version 2018-11-29
 */
@Controller
@RequestMapping(value = "${adminPath}/shopwarehouse/shopWarehose")
public class ShopWarehoseController extends BaseController {

	@Autowired
	private ShopWarehoseService shopWarehoseService;
	
	@ModelAttribute
	public ShopWarehose get(@RequestParam(required=false) String id) {
		ShopWarehose entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopWarehoseService.get(id);
		}
		if (entity == null){
			entity = new ShopWarehose();
		}
		return entity;
	}
	
	@RequiresPermissions("shopwarehouse:shopWarehose:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShopWarehose shopWarehose, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ShopWarehose> page = shopWarehoseService.findPage(new Page<ShopWarehose>(request, response), shopWarehose); 
		model.addAttribute("page", page);
		return "modules/shopwarehouse/shopWarehoseList";
	}

	@RequiresPermissions("shopwarehouse:shopWarehose:view")
	@RequestMapping(value = "form")
	public String form(ShopWarehose shopWarehose, Model model) {
		model.addAttribute("shopWarehose", shopWarehose);
		return "modules/shopwarehouse/shopWarehoseForm";
	}

	@RequiresPermissions("shopwarehouse:shopWarehose:edit")
	@RequestMapping(value = "save")
	public String save(ShopWarehose shopWarehose, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shopWarehose)){
			return form(shopWarehose, model);
		}
		shopWarehoseService.save(shopWarehose);
		addMessage(redirectAttributes, "保存仓库成功");
		return "redirect:"+Global.getAdminPath()+"/shopwarehouse/shopWarehose/?repage";
	}
	
	@RequiresPermissions("shopwarehouse:shopWarehose:edit")
	@RequestMapping(value = "delete")
	public String delete(ShopWarehose shopWarehose, RedirectAttributes redirectAttributes) {
		shopWarehoseService.delete(shopWarehose);
		addMessage(redirectAttributes, "删除仓库成功");
		return "redirect:"+Global.getAdminPath()+"/shopwarehouse/shopWarehose/?repage";
	}

}