package com.youtao.portal.service;

import java.io.IOException;
import java.util.Objects;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.youtao.common.service.RedisService;
import com.youtao.manager.pojo.ItemDesc;
import com.youtao.manager.pojo.ItemParamValue;
import com.youtao.portal.bean.Item;

/**
 * @title: ItemService
 * @description: 
 * @Copyright: Copyright (c) 2018
 * @Company: lgxkdream.github.io
 * @author gang.li
 * @version 1.0.0
 * @since 2018年3月9日 下午4:27:07
 */
@Service
public class ItemService {
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	private static final String REDIS_ITEM_KEY = "YOUTAO_PORTAL_ITEM_";
	private static final String REDIS_ITEM_DESC_KEY = "YOUTAO_PORTAL_ITEM_DESC_";
	private static final String REDIS_ITEM_PARAM_VALUE_KEY = "YOUTAO_PORTAL_ITEM_PARAM_VALUE_";
	private static final int REDIS_EXPIRE = 60 * 60 * 12;
	
	@Value("${item.url}")
	private String itemUrl;
	@Value("${item.desc.url}")
	private String itemDescUrl;
	@Value("${item.param.value.url}")
	private String itemParamValueUrl;
	
	@Autowired
	private ApiService apiService;
	
	@Autowired
	private RedisService redisService;

	public Item queryItemByItemId(Long itemId) {
		try {
			Item cacheItem = this.redisService.get(REDIS_ITEM_KEY + itemId, Item.class);
			if (!Objects.isNull(cacheItem)) {
				return cacheItem;
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String jsonData = this.apiService.doGet(itemUrl + itemId);
			if (StringUtils.isNotBlank(jsonData)) {
				try {
					this.redisService.set(REDIS_ITEM_KEY + itemId, jsonData, REDIS_EXPIRE);
				} catch (Exception e) {
					// 缓存不应影响原有逻辑
					e.printStackTrace();
				}
				return MAPPER.readValue(jsonData, Item.class);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ItemDesc queryItemDescByItemId(Long itemId) {
		try {
			ItemDesc cacheItemDesc = this.redisService.get(REDIS_ITEM_DESC_KEY + itemId, ItemDesc.class);
			if (!Objects.isNull(cacheItemDesc)) {
				return cacheItemDesc;
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String jsonData = this.apiService.doGet(itemDescUrl + itemId);
			if (StringUtils.isNotBlank(jsonData)) {
				try {
					this.redisService.set(REDIS_ITEM_DESC_KEY + itemId, jsonData, REDIS_EXPIRE);
				} catch (Exception e) {
					// 缓存不应影响原有逻辑
					e.printStackTrace();
				}
				return MAPPER.readValue(jsonData, ItemDesc.class);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public String queryItemParamValueByItemId(Long itemId) {
		try {
			String cacheData = this.redisService.get(REDIS_ITEM_PARAM_VALUE_KEY + itemId);
			if (StringUtils.isNoneBlank(cacheData)) {
				return cacheData;
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			String jsonData = this.apiService.doGet(itemParamValueUrl + itemId);
			if (StringUtils.isNotBlank(jsonData)) {
				ItemParamValue itemParamValue = MAPPER.readValue(jsonData, ItemParamValue.class);
				String paramData = itemParamValue.getParamData();
				ArrayNode arrayNode = (ArrayNode) MAPPER.readTree(paramData);
				StringBuilder sb = new StringBuilder();
				sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\"><tbody>");
				for (JsonNode jsonNode : arrayNode) {
					sb.append("<tr><th class=\"tdTitle\" colspan=\"2\">");
					sb.append(jsonNode.get("group").asText());
					sb.append("</th></tr>");
					ArrayNode params = (ArrayNode) jsonNode.get("params");
					for (JsonNode param : params) {
						sb.append("<tr><td class=\"tdTitle\">");
						sb.append(param.get("k").asText());
						sb.append("</td><td>");
						sb.append(param.get("v").asText());
						sb.append("</td></tr>");
					}
				}
				sb.append("</tbody></table>");
				String result = sb.toString();
				try {
					this.redisService.set(REDIS_ITEM_PARAM_VALUE_KEY + itemId, result, REDIS_EXPIRE);
				} catch (Exception e) {
					// 缓存不应影响原有逻辑
					e.printStackTrace();
				}
				return result;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
