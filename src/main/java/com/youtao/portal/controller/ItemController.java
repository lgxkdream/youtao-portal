package com.youtao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.youtao.manager.pojo.ItemDesc;
import com.youtao.portal.bean.Item;
import com.youtao.portal.service.ItemService;

/**
 * @title: ItemController
 * @description: 
 * @Copyright: Copyright (c) 2018
 * @Company: lgxkdream.github.io
 * @author gang.li
 * @version 1.0.0
 * @since 2018年3月9日 下午4:26:54
 */
@Controller
@RequestMapping("/item")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping(value = "{itemId}", method = RequestMethod.GET)
	public ModelAndView queryItem(@PathVariable("itemId") Long itemId) {
		ModelAndView mv = new ModelAndView("item");
		Item item = this.itemService.queryItemByItemId(itemId);
		ItemDesc itemDesc = this.itemService.queryItemDescByItemId(itemId);
		String itemParam = this.itemService.queryItemParamValueByItemId(itemId);
		mv.addObject("item", item);
		mv.addObject("itemDesc", itemDesc);
		mv.addObject("itemParam", itemParam);
		return mv;
	}

}
