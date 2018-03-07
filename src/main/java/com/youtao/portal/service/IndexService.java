package com.youtao.portal.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

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

}
