/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shopcatgory.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.shopcatgory.entity.ShopCategory;
import com.thinkgem.jeesite.modules.shopcatgory.service.ShopCategoryService;

/**
 * 商品分类Controller
 * @author zhb
 * @version 2018-11-29
 */
@Controller
@RequestMapping(value = "${adminPath}/shopcatgory/shopCategory")
public class ShopCategoryController extends BaseController {

	@Autowired
	private ShopCategoryService shopCategoryService;
	
	@ModelAttribute
	public ShopCategory get(@RequestParam(required=false) String id) {
		ShopCategory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopCategoryService.get(id);
		}
		if (entity == null){
			entity = new ShopCategory();
		}
		return entity;
	}
	
	@RequiresPermissions("shopcatgory:shopCategory:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShopCategory shopCategory, HttpServletRequest request, HttpServletResponse response, Model model) {
		List<ShopCategory> list = shopCategoryService.findList(shopCategory); 
		model.addAttribute("list", list);
		return "modules/shopcatgory/shopCategoryList";
	}

	@RequiresPermissions("shopcatgory:shopCategory:view")
	@RequestMapping(value = "form")
	public String form(ShopCategory shopCategory, Model model) {
		if (shopCategory.getParent()!=null && StringUtils.isNotBlank(shopCategory.getParent().getId())){
			shopCategory.setParent(shopCategoryService.get(shopCategory.getParent().getId()));
			// 获取排序号，最末节点排序号+30
			if (StringUtils.isBlank(shopCategory.getId())){
				ShopCategory shopCategoryChild = new ShopCategory();
				shopCategoryChild.setParent(new ShopCategory(shopCategory.getParent().getId()));
				List<ShopCategory> list = shopCategoryService.findList(shopCategory); 
				if (list.size() > 0){
					shopCategory.setSort(list.get(list.size()-1).getSort());
					if (shopCategory.getSort() != null){
						shopCategory.setSort(shopCategory.getSort() + 30);
					}
				}
			}
		}
		if (shopCategory.getSort() == null){
			shopCategory.setSort(30);
		}
		model.addAttribute("shopCategory", shopCategory);
		return "modules/shopcatgory/shopCategoryForm";
	}

	@RequiresPermissions("shopcatgory:shopCategory:edit")
	@RequestMapping(value = "save")
	public String save(ShopCategory shopCategory, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shopCategory)){
			return form(shopCategory, model);
		}
		shopCategoryService.save(shopCategory);
		addMessage(redirectAttributes, "保存商品分类成功");
		return "redirect:"+Global.getAdminPath()+"/shopcatgory/shopCategory/?repage";
	}
	
	@RequiresPermissions("shopcatgory:shopCategory:edit")
	@RequestMapping(value = "delete")
	public String delete(ShopCategory shopCategory, RedirectAttributes redirectAttributes) {
		shopCategoryService.delete(shopCategory);
		addMessage(redirectAttributes, "删除商品分类成功");
		return "redirect:"+Global.getAdminPath()+"/shopcatgory/shopCategory/?repage";
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<ShopCategory> list = shopCategoryService.findList(new ShopCategory());
		for (int i=0; i<list.size(); i++){
			ShopCategory e = list.get(i);
			if (StringUtils.isBlank(extId) || (extId!=null && !extId.equals(e.getId()) && e.getParentIds().indexOf(","+extId+",")==-1)){
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}
	
}