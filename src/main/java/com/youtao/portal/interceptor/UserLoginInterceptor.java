package com.youtao.portal.interceptor;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.youtao.common.utils.CookieUtils;
import com.youtao.portal.bean.User;
import com.youtao.portal.service.UserService;

/**
 * @title: UserLoginInterceptor
 * @description: 用户登录拦截器
 * @Copyright: Copyright (c) 2018
 * @Company: lgxkdream.github.io
 * @author gang.li
 * @version 1.0.0
 * @since 2018年3月18日 上午9:17:24
 */
public class UserLoginInterceptor implements HandlerInterceptor {
	
	private static final String COOKIE_NAME = "YT_TOKEN";
	
	@Autowired
	private UserService userService;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = CookieUtils.getCookieValue(request, COOKIE_NAME);
		if (StringUtils.isNotBlank(token)) {
			User user = userService.queryUserByToken(token);
			if (!Objects.isNull(user)) {
				// 处于登录状态
				return true;
			}
			// 处于登录超时状态
		}
		// 处于未登录状态
		response.sendRedirect("http://sso.youtao.com/user/login.html");
		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

}
