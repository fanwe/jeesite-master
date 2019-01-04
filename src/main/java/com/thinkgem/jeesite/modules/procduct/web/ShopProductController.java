/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.procduct.web;

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
import com.thinkgem.jeesite.modules.procduct.entity.ShopProduct;
import com.thinkgem.jeesite.modules.procduct.service.ShopProductService;
import com.thinkgem.jeesite.modules.shopbrand.entity.ShopBrand;
import com.thinkgem.jeesite.modules.shopbrand.service.ShopBrandService;
import com.thinkgem.jeesite.modules.shopcatgory.entity.ShopCategory;
import com.thinkgem.jeesite.modules.shopcatgory.service.ShopCategoryService;
import com.thinkgem.jeesite.modules.shopwarehouse.entity.ShopWarehose;
import com.thinkgem.jeesite.modules.shopwarehouse.service.ShopWarehoseService;

/**
 * 商品管理Controller
 * @author zhb
 * @version 2018-11-30
 */
@Controller
@RequestMapping(value = "${adminPath}/procduct/shopProduct")
public class ShopProductController extends BaseController {

	@Autowired
	private ShopProductService shopProductService;
	@Autowired
	private ShopBrandService shopBrandService;
	@Autowired
	private ShopWarehoseService shopWarehoseService;
	@ModelAttribute
	public ShopProduct get(@RequestParam(required=false) String id) {
		ShopProduct entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopProductService.get(id);
		}
		if (entity == null){
			entity = new ShopProduct();
		}
		return entity;
	}
	
	@RequiresPermissions("procduct:shopProduct:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShopProduct shopProduct, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ShopProduct> page = shopProductService.findPage(new Page<ShopProduct>(request, response), shopProduct); 
		model.addAttribute("page", page);
		return "modules/procduct/shopProductList";
	}

	@RequiresPermissions("procduct:shopProduct:view")
	@RequestMapping(value = "form")
	public String form(ShopProduct shopProduct, Model model) {
		model.addAttribute("shopProduct", shopProduct);
		List<ShopBrand> ShopBrandList = shopBrandService.findList(new ShopBrand());
		model.addAttribute("ShopBrandList", ShopBrandList);
		List<ShopWarehose> warehoseList = shopWarehoseService.findList(new ShopWarehose());
		model.addAttribute("warehoseList", warehoseList);
		return "modules/procduct/shopProductForm";
	}

	@RequiresPermissions("procduct:shopProduct:edit")
	@RequestMapping(value = "save")
	public String save(ShopProduct shopProduct, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shopProduct)){
			return form(shopProduct, model);
		}
		shopProductService.save(shopProduct);
		addMessage(redirectAttributes, "保存商品成功");
		return "redirect:"+Global.getAdminPath()+"/procduct/shopProduct/?repage";
	}
	
	@RequiresPermissions("procduct:shopProduct:edit")
	@RequestMapping(value = "delete")
	public String delete(ShopProduct shopProduct, RedirectAttributes redirectAttributes) {
		shopProductService.delete(shopProduct);
		addMessage(redirectAttributes, "删除商品成功");
		return "redirect:"+Global.getAdminPath()+"/procduct/shopProduct/?repage";
	}

}