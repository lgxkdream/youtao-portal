package com.youtao.portal.bean;

import org.apache.commons.lang3.StringUtils;

/**
 * @title: Item
 * @description: 商品扩展实体
 * @Copyright: Copyright (c) 2018
 * @Company: lgxkdream.github.io
 * @author gang.li
 * @version 1.0.0
 * @since 2018年3月9日 下午4:44:20
 */
public class Item extends com.youtao.manager.pojo.Item {
	
	public String[] getImages() {
		return StringUtils.split(super.getImage(), ",");
	}

}
