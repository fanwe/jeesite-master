/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.order.web;

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
import com.thinkgem.jeesite.modules.order.entity.ShopOrder;
import com.thinkgem.jeesite.modules.order.service.ShopOrderService;
import com.thinkgem.jeesite.modules.shopuser.entity.ShopUser;
import com.thinkgem.jeesite.modules.shopuser.service.ShopUserService;
import com.thinkgem.jeesite.modules.utiles.OrdernoUtile;

/**
 * 订单管理Controller
 * @author zhb
 * @version 2018-12-02
 */
@Controller
@RequestMapping(value = "${adminPath}/order/shopOrder")
public class ShopOrderController extends BaseController {

	@Autowired
	private ShopOrderService shopOrderService;
	@Autowired
	private ShopUserService shopUserService;
	@ModelAttribute
	public ShopOrder get(@RequestParam(required=false) String id) {
		ShopOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shopOrderService.get(id);
		}
		if (entity == null){
			entity = new ShopOrder();
		}
		return entity;
	}

	@RequiresPermissions("order:shopOrder:view")
	@RequestMapping(value = {"list", ""})
	public String list(ShopOrder shopOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ShopOrder> page = shopOrderService.findPage(new Page<ShopOrder>(request, response), shopOrder); 
		model.addAttribute("page", page);
		return "modules/order/shopOrderList";
	}
	/**
	 * 退款订单页面显示
	 * @param shopOrder
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("order:shopOrder:view")
	@RequestMapping(value = "refundList")
	public String refundList(ShopOrder shopOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		shopOrder.setOrderstatus(7);
		Page<ShopOrder> page = shopOrderService.findPage(new Page<ShopOrder>(request, response), shopOrder); 
		model.addAttribute("page", page);
		return "modules/order/shopOrderList";
	}


	/**
	 * 退货订单页面显示
	 * @param shopOrder
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequiresPermissions("order:shopOrder:view")
	@RequestMapping(value = "returnList")
	public String returnList(ShopOrder shopOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		shopOrder.setOrderstatus(8);
		Page<ShopOrder> page = shopOrderService.findPage(new Page<ShopOrder>(request, response), shopOrder); 
		model.addAttribute("page", page);
		return "modules/order/shopOrderList";
	}

	@RequiresPermissions("order:shopOrder:view")
	@RequestMapping(value = "form")
	public String form(ShopOrder shopOrder, Model model) {
		model.addAttribute("shopOrder", shopOrder);
		ShopUser shopUser = new ShopUser();
		shopUser.setIslock(0);
		List<ShopUser> users= shopUserService.findList(shopUser );
		model.addAttribute("users",users);
		return "modules/order/shopOrderForm";
	}

	@RequiresPermissions("order:shopOrder:edit")
	@RequestMapping(value = "save")
	public String save(ShopOrder shopOrder, Model model, RedirectAttributes redirectAttributes) {
		shopOrder.setOrderno(OrdernoUtile.getOrderIdByUUId());
		if (!beanValidator(model, shopOrder)){
			return form(shopOrder, model);
		}
		shopOrderService.save(shopOrder);
		addMessage(redirectAttributes, "保存订单成功");
		return "redirect:"+Global.getAdminPath()+"/order/shopOrder/?repage";
	}

	@RequiresPermissions("order:shopOrder:edit")
	@RequestMapping(value = "delete")
	public String delete(ShopOrder shopOrder, RedirectAttributes redirectAttributes) {
		shopOrderService.delete(shopOrder);
		addMessage(redirectAttributes, "删除订单成功");
		return "redirect:"+Global.getAdminPath()+"/order/shopOrder/?repage";
	}

}