/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shopactive.web;

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
import com.thinkgem.jeesite.modules.shopactive.entity.ShopPromotionActivity;
import com.thinkgem.jeesite.modules.shopactive.service.ShopPromotionActivityService;

/**
 * 促销活动管理Controller
 * @author zhb
 * @version 2018-12-02
 */
@Controller
@RequestMapping(value = "${adminPath}/shopactive/shopPromotionActivity")
public class ShopPromotionActivityController extends BaseController {

	@Autowired
	private ShopPromotionActivityService shopPromotionActivityService;
	
	@ModelAttribute
	public ShopPromotionActivity get(@RequestParam(required=false) String id) {
		ShopPromotionActivity entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopPromotionActivityService.get(id);
		}
		if (entity == null){
			entity = new ShopPromotionActivity();
		}
		return entity;
	}
	
	@RequiresPermissions("shopactive:shopPromotionActivity:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShopPromotionActivity shopPromotionActivity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ShopPromotionActivity> page = shopPromotionActivityService.findPage(new Page<ShopPromotionActivity>(request, response), shopPromotionActivity); 
		model.addAttribute("page", page);
		return "modules/shopactive/shopPromotionActivityList";
	}

	@RequiresPermissions("shopactive:shopPromotionActivity:view")
	@RequestMapping(value = "form")
	public String form(ShopPromotionActivity shopPromotionActivity, Model model) {
		model.addAttribute("shopPromotionActivity", shopPromotionActivity);
		return "modules/shopactive/shopPromotionActivityForm";
	}

	@RequiresPermissions("shopactive:shopPromotionActivity:edit")
	@RequestMapping(value = "save")
	public String save(ShopPromotionActivity shopPromotionActivity, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shopPromotionActivity)){
			return form(shopPromotionActivity, model);
		}
		shopPromotionActivityService.save(shopPromotionActivity);
		addMessage(redirectAttributes, "保存促销活动成功");
		return "redirect:"+Global.getAdminPath()+"/shopactive/shopPromotionActivity/?repage";
	}
	
	@RequiresPermissions("shopactive:shopPromotionActivity:edit")
	@RequestMapping(value = "delete")
	public String delete(ShopPromotionActivity shopPromotionActivity, RedirectAttributes redirectAttributes) {
		shopPromotionActivityService.delete(shopPromotionActivity);
		addMessage(redirectAttributes, "删除促销活动成功");
		return "redirect:"+Global.getAdminPath()+"/shopactive/shopPromotionActivity/?repage";
	}

}