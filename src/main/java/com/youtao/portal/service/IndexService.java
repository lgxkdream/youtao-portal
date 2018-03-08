package com.youtao.portal.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youtao.common.bean.EasyUIResult;
import com.youtao.manager.pojo.Content;

/**
 * @title: IndexService
 * @description: 首页Service
 * @Copyright: Copyright (c) 2018
 * @Company: lgxkdream.github.io
 * @author gang.li
 * @version 1.0.0
 * @since 2018年3月7日 下午5:14:31
 */
@Service
public class IndexService {

	private static final ObjectMapper MAPPER = new ObjectMapper();

	@Autowired
	private ApiService apiService;

	@Value("${index.aadvertisement1}")
	private String aadvertisement1;
	@Value("${index.floor.aadvertisement2}")
	private String floorAadvertisement2;

	/**
	 * 首页轮播大广告
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String queryAadvertisement1() {
		try {
			String jsonData = this.apiService.doGet(aadvertisement1);
			if (StringUtils.isBlank(jsonData)) {
				return null;
			}
			EasyUIResult easyUIResult = EasyUIResult.formatToList(jsonData, Content.class);
			List<Object> result = new ArrayList<Object>();
			for (Content content : (List<Content>) easyUIResult.getRows()) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				map.put("srcB", content.getPic());
				map.put("height", 240);
				map.put("alt", content.getTitle());
				map.put("width", 670);
				map.put("src", content.getPic());
				map.put("widthB", 550);
				map.put("href", content.getUrl());
				map.put("heightB", 240);
				result.add(map);
			}
			return MAPPER.writeValueAsString(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 首页楼层中间广告
	 * @return
	 */
	public String queryFloorAadvertisement2() {
		try {
			Map<String, Object> result = new LinkedHashMap<String, Object>();
			this.queryFloorAadvertisement2(result, 62L);
			this.queryFloorAadvertisement2(result, 63L);
			this.queryFloorAadvertisement2(result, 64L);
			this.queryFloorAadvertisement2(result, 65L);
			this.queryFloorAadvertisement2(result, 66L);
			return MAPPER.writeValueAsString(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	private void queryFloorAadvertisement2(Map<String, Object> result, Long cid) throws ClientProtocolException, IOException {
		String jsonData = this.apiService.doGet(StringUtils.replace(floorAadvertisement2, "{cid}", cid + ""));
		if (StringUtils.isBlank(jsonData)) {
			return;
		}
		EasyUIResult easyUIResult = EasyUIResult.formatToList(jsonData, Content.class);
		List<Content> contentList = (List<Content>) easyUIResult.getRows();
		if (CollectionUtils.isEmpty(contentList)) {
			return;
		}
		Map<String, Object> data = new LinkedHashMap<String, Object>();
		int index = 1;
		for (Content content : contentList) {
			Map<String, Object> map = new LinkedHashMap<String, Object>();
			map.put("d", content.getPic()); // 图片
			map.put("e", "0"); // 图片标签
			map.put("c", content.getSubTitle());  // 价格
			map.put("a", content.getUrl()); // 商品url
			map.put("b", content.getTitle()); // 标题
			map.put("f", 1);
			data.put(index + "", map);
			index++;
		}
		result.put(cid + "", data);
	}

}
