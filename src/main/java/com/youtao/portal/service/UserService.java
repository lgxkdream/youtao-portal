package com.youtao.portal.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.youtao.portal.bean.User;

@Service
public class UserService {
	
	private static final ObjectMapper MAPPER = new ObjectMapper();
	
	@Autowired
	private ApiService apiService;
	
	@Value("${sso.user.query.url}")
	private String userQueryUrl;
	
	public User queryUserByToken(String token) {
		try {
			String jsonData = this.apiService.doGet(userQueryUrl + token);
			if (StringUtils.isNotBlank(jsonData)) {
				return MAPPER.readValue(jsonData, User.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
