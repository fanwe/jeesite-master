/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shopbrand.web;

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
import com.thinkgem.jeesite.modules.shopbrand.entity.ShopBrand;
import com.thinkgem.jeesite.modules.shopbrand.service.ShopBrandService;

/**
 * 商品品牌Controller
 * @author zhb
 * @version 2018-11-29
 */
@Controller
@RequestMapping(value = "${adminPath}/shopbrand/shopBrand")
public class ShopBrandController extends BaseController {

	@Autowired
	private ShopBrandService shopBrandService;
	
	@ModelAttribute
	public ShopBrand get(@RequestParam(required=false) String id) {
		ShopBrand entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopBrandService.get(id);
		}
		if (entity == null){
			entity = new ShopBrand();
		}
		return entity;
	}
	
	@RequiresPermissions("shopbrand:shopBrand:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShopBrand shopBrand, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ShopBrand> page = shopBrandService.findPage(new Page<ShopBrand>(request, response), shopBrand); 
		model.addAttribute("page", page);
		return "modules/shopbrand/shopBrandList";
	}

	@RequiresPermissions("shopbrand:shopBrand:view")
	@RequestMapping(value = "form")
	public String form(ShopBrand shopBrand, Model model) {
		model.addAttribute("shopBrand", shopBrand);
		return "modules/shopbrand/shopBrandForm";
	}

	@RequiresPermissions("shopbrand:shopBrand:edit")
	@RequestMapping(value = "save")
	public String save(ShopBrand shopBrand, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shopBrand)){
			return form(shopBrand, model);
		}
		shopBrandService.save(shopBrand);
		addMessage(redirectAttributes, "保存商品品牌成功");
		return "redirect:"+Global.getAdminPath()+"/shopbrand/shopBrand/?repage";
	}
	
	@RequiresPermissions("shopbrand:shopBrand:edit")
	@RequestMapping(value = "delete")
	public String delete(ShopBrand shopBrand, RedirectAttributes redirectAttributes) {
		shopBrandService.delete(shopBrand);
		addMessage(redirectAttributes, "删除商品品牌成功");
		return "redirect:"+Global.getAdminPath()+"/shopbrand/shopBrand/?repage";
	}

}