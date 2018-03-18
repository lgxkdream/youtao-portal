package com.youtao.portal.controller;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.youtao.portal.bean.Item;
import com.youtao.portal.bean.Order;
import com.youtao.portal.service.ItemService;
import com.youtao.portal.service.OrderService;

/**
 * @title: OrderController
 * @description: 订单管理Controller
 * @Copyright: Copyright (c) 2018
 * @Company: lgxkdream.github.io
 * @author gang.li
 * @version 1.0.0
 * @since 2018年3月13日 下午7:33:13
 */
@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	private ItemService itemService;
	
	@Autowired
	private OrderService orderService;
	
	/**
	 * 订单确认页面
	 * @param itemId
	 * @return
	 */
	@RequestMapping(value = "{itemId}", method = RequestMethod.GET)
	public ModelAndView toOrder(@PathVariable("itemId") Long itemId) {
		ModelAndView mv = new ModelAndView("order");
		Item item = this.itemService.queryItemByItemId(itemId);
		mv.addObject("item", item);
		return mv;
	}
	
	/**
	 * 订单提交
	 * @param order
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	public Map<String, Object> submit(Order order) {
		Map<String, Object> result = new HashMap<String, Object>();
		String orderId = this.orderService.submit(order);
		if (StringUtils.isBlank(orderId)) {
			// 订单提交失败
			result.put("status", 300);
		} else {
			// 订单提交成功
			result.put("status", 200);
			result.put("data", orderId);
		}
		return result;
	}
	
	@RequestMapping(value = "success", method = RequestMethod.GET)
	public String success() {
		return "success";
	}

}
