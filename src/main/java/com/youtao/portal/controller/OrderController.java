package com.youtao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.youtao.portal.bean.Item;
import com.youtao.portal.service.ItemService;

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

}
