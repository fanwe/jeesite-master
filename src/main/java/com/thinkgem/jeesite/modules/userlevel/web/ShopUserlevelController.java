/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.userlevel.web;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.engine.impl.util.json.JSONArray;
import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.thinkgem.jeesite.common.config.Global;
import com.thinkgem.jeesite.common.persistence.Page;
import com.thinkgem.jeesite.common.web.BaseController;
import com.thinkgem.jeesite.common.utils.StringUtils;
import com.thinkgem.jeesite.modules.userlevel.entity.ShopUserlevel;
import com.thinkgem.jeesite.modules.userlevel.service.ShopUserlevelService;

/**
 * 会员等级Controller
 * @author zhb
 * @version 2018-12-01
 */
@Controller
@RequestMapping(value = "${adminPath}/userlevel/shopUserlevel")
public class ShopUserlevelController extends BaseController {

	@Autowired
	private ShopUserlevelService shopUserlevelService;
	
	@ModelAttribute
	public ShopUserlevel get(@RequestParam(required=false) String id) {
		ShopUserlevel entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopUserlevelService.get(id);
		}
		if (entity == null){
			entity = new ShopUserlevel();
		}
		return entity;
	}
	
	@RequiresPermissions("userlevel:shopUserlevel:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShopUserlevel shopUserlevel, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ShopUserlevel> page = shopUserlevelService.findPage(new Page<ShopUserlevel>(request, response), shopUserlevel); 
		model.addAttribute("page", page);
		return "modules/userlevel/shopUserlevelList";
	}

	@RequiresPermissions("userlevel:shopUserlevel:view")
	@RequestMapping(value = {"userEchart"})
	public String userEchart(ShopUserlevel shopUserlevel, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<ShopUserlevel> page = shopUserlevelService.findPage(new Page<ShopUserlevel>(request, response), shopUserlevel); 
//		model.addAttribute("page", page);
		ArrayList<String> arrayList = new ArrayList<String>();
		arrayList.add("赵慧斌");
		arrayList.add("张三");
		arrayList.add("李四");
		return "modules/userlevel/countUserEchart";
	}
	
	@RequiresPermissions("userlevel:shopUserlevel:view")
	@RequestMapping(value = {"userEchartjsondata"},method=RequestMethod.GET)
	@ResponseBody
	public String userEchartJsonData(ShopUserlevel shopUserlevel, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<ShopUserlevel> page = shopUserlevelService.findPage(new Page<ShopUserlevel>(request, response), shopUserlevel); 
//		model.addAttribute("page", page);
		ArrayList<Integer> arrayList = new ArrayList<Integer>();
		arrayList.add(200);
		arrayList.add(280);
		arrayList.add(100);
		arrayList.add(100);
		arrayList.add(400);
		arrayList.add(340);
		JSONArray array = new JSONArray(arrayList);
		JSONObject jo = new JSONObject();
		jo.put("data", array);
		ArrayList<String> categories = new ArrayList<String>();
		categories.add("大众");
		categories.add("奥迪");
		categories.add("宝马");
		categories.add("雷诺");
		categories.add("雪佛兰");
		categories.add("长城");
		jo.put("categories", categories);
		return jo.toString();
	}
	@RequiresPermissions("userlevel:shopUserlevel:view")
	@RequestMapping(value = "form")
	public String form(ShopUserlevel shopUserlevel, Model model) {
		model.addAttribute("shopUserlevel", shopUserlevel);
		return "modules/userlevel/shopUserlevelForm";
	}

	@RequiresPermissions("userlevel:shopUserlevel:edit")
	@RequestMapping(value = "save")
	public String save(ShopUserlevel shopUserlevel, Model model, RedirectAttributes redirectAttributes) {
		if (!beanValidator(model, shopUserlevel)){
			return form(shopUserlevel, model);
		}
		shopUserlevelService.save(shopUserlevel);
		addMessage(redirectAttributes, "保存会员等级成功");
		return "redirect:"+Global.getAdminPath()+"/userlevel/shopUserlevel/?repage";
	}
	
	@RequiresPermissions("userlevel:shopUserlevel:edit")
	@RequestMapping(value = "delete")
	public String delete(ShopUserlevel shopUserlevel, RedirectAttributes redirectAttributes) {
		shopUserlevelService.delete(shopUserlevel);
		addMessage(redirectAttributes, "删除会员等级成功");
		return "redirect:"+Global.getAdminPath()+"/userlevel/shopUserlevel/?repage";
	}

}