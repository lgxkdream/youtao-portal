package com.taotao.portal.httpclient;

/**
 * @title: HttpResult
 * @description: POST请求响应结果
 * @Copyright: Copyright (c) 2018
 * @Company: lgxkdream.github.io
 * @author gang.li
 * @version 1.0.0
 * @since 2018年3月7日 下午4:11:41
 */
public class HttpResult {

	private Integer code;

	private String data;
	
	public HttpResult() {
		super();
	}

	public HttpResult(Integer code, String data) {
		super();
		this.code = code;
		this.data = data;
	}

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
