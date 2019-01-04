/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.shopuser.web;

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
import com.thinkgem.jeesite.modules.shopuser.entity.ShopUser;
import com.thinkgem.jeesite.modules.shopuser.service.ShopUserService;
import com.thinkgem.jeesite.modules.userlevel.entity.ShopUserlevel;
import com.thinkgem.jeesite.modules.userlevel.service.ShopUserlevelService;

/**
 * 会员管理Controller
 * @author zhb
 * @version 2018-12-01
 */
@Controller
@RequestMapping(value = "${adminPath}/shopuser/shopUser")
public class ShopUserController extends BaseController {

	@Autowired
	private ShopUserService shopUserService;
	@Autowired
	private ShopUserlevelService  shopUserlevelService;
	
	@ModelAttribute
	public ShopUser get(@RequestParam(required=false) String id) {
		ShopUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopUserService.get(id);
		}
		if (entity == null){
			entity = new ShopUser();
		}
		return entity;
	}
	
	@RequiresPermissions("shopuser:shopUser:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShopUser shopUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		shopUser.setIslock(0);
		Page<ShopUser> page = shopUserService.findPage(new Page<ShopUser>(request, response), shopUser); 
		model.addAttribute("page", page);
		return "modules/shopuser/shopUserList";
	}
	
	@RequiresPermissions("shopuser:shopUser:view")
	@RequestMapping(value = {"unlocklist"})
	public String islockList(ShopUser shopUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		shopUser.setIslock(1);
		Page<ShopUser> page = shopUserService.findPage(new Page<ShopUser>(request, response), shopUser); 
		model.addAttribute("page", page);
		return "modules/shopuser/shopUserlockList";
	}
	
	@RequiresPermissions("shopuser:shopUser:view")
	@RequestMapping(value = "form")
	public String form(ShopUser shopUser, Model model) {
		shopUser.setIslock(0);
		model.addAttribute("shopUser", shopUser);
		List<ShopUserlevel> userlevellist = shopUserlevelService.findList(new ShopUserlevel());
		model.addAttribute("userlevellist", userlevellist);
		return "modules/shopuser/shopUserForm";
	}

	@RequiresPermissions("shopuser:shopUser:edit")
	@RequestMapping(value = "save")
	public String save(ShopUser shopUser, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shopUser)){
			return form(shopUser, model);
		}
		shopUser.setIslock(0);
		shopUserService.save(shopUser);
		addMessage(redirectAttributes, "保存会员成功");
		return "redirect:"+Global.getAdminPath()+"/shopuser/shopUser/?repage";
	}
	
	@RequiresPermissions("shopuser:shopUser:edit")
	@RequestMapping(value = "delete")
	public String delete(ShopUser shopUser, RedirectAttributes redirectAttributes) {
		shopUserService.delete(shopUser);
		addMessage(redirectAttributes, "删除会员成功");
		return "redirect:"+Global.getAdminPath()+"/shopuser/shopUser/?repage";
	}
	
	@RequiresPermissions("shopuser:shopUser:edit")
	@RequestMapping(value = "islock")
	public String islock(ShopUser shopUser, RedirectAttributes redirectAttributes) {
		shopUser.setIslock(1);
		shopUserService.islock(shopUser);
		
		addMessage(redirectAttributes, "冻结会员成功");
		return "redirect:"+Global.getAdminPath()+"/shopuser/shopUser/?repage";
	}

	@RequiresPermissions("shopuser:shopUser:edit")
	@RequestMapping(value = "unlock")
	public String unlock(ShopUser shopUser, RedirectAttributes redirectAttributes) {
		shopUser.setIslock(0);
		shopUserService.islock(shopUser);
		
		addMessage(redirectAttributes, "解冻会员成功");
		return "redirect:"+Global.getAdminPath()+"/shopuser/shopUser/unlocklist";
	}
}