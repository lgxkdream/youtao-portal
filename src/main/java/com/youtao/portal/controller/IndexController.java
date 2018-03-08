package com.youtao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.youtao.portal.service.IndexService;

/**
 * @title: IndexController
 * @description: 
 * @Copyright: Copyright (c) 2018
 * @Company: lgxkdream.github.io
 * @author gang.li
 * @version 1.0.0
 * @since 2018年3月4日 下午7:02:30
 */
@Controller
@RequestMapping("/index")
public class IndexController {
	
	@Autowired
	private IndexService indexService;
	
	/**
	 * 首页展示
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView index() {
		ModelAndView mv = new ModelAndView("index");
		// 首页轮播大广告
		String advertisement1 = this.indexService.queryAadvertisement1();
		mv.addObject("advertisement1", advertisement1);
		// 楼层中广告
		String floorAadvertisement2 = this.indexService.queryFloorAadvertisement2();
		System.out.println(floorAadvertisement2);
		mv.addObject("floorAadvertisement2", floorAadvertisement2);
		return mv;
	}

}
