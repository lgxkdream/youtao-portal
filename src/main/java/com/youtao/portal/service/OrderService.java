package com.youtao.portal.service;

import java.io.IOException;

import org.apache.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youtao.portal.bean.Order;
import com.youtao.portal.bean.User;
import com.youtao.portal.httpclient.HttpResult;
import com.youtao.portal.interceptor.UserLoginInterceptor;

/**
 * @title: OrderService
 * @description: 订单Service
 * @Copyright: Copyright (c) 2018
 * @Company: lgxkdream.github.io
 * @author gang.li
 * @version 1.0.0
 * @since 2018年3月18日 上午11:49:23
 */
@Service
public class OrderService {
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@Autowired
	private ApiService apiService;
	
	@Value("${order.create.url}")
	private String createOrderUrl;

	public String submit(Order order) {
		try {
			User user = UserLoginInterceptor.getUser();
			order.setUserId(user.getId());
			order.setBuyerNick(user.getUsername());
			HttpResult httpResult = this.apiService.doPost(createOrderUrl, MAPPER.writeValueAsString(order));
			if (200 == httpResult.getCode().intValue()) {
				JsonNode jsonNode = MAPPER.readTree(httpResult.getData());
				if (200 == jsonNode.get("status").intValue()) {
					return jsonNode.get("data").asText();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
