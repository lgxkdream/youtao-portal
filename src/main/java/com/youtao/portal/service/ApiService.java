package com.youtao.portal.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.youtao.portal.httpclient.HttpResult;

@Service
public class ApiService implements BeanFactoryAware {
	
	private BeanFactory beanFactory;

	@Autowired
	private RequestConfig requestConfig;

	/**
	 * 不带参数的GET请求
	 * @param url 请求路径
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public String doGet(String url) throws ClientProtocolException, IOException {
		// 创建http GET请求
		HttpGet httpGet = new HttpGet(url);
		httpGet.setConfig(requestConfig);

		CloseableHttpResponse response = null;
		try {
			// 执行请求
			response = this.getHttpclient().execute(httpGet);
			// 判断返回状态是否为200
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity(), "UTF-8");
			}
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return null;
	}
	
	/**
	 * 带有参数的GET请求
	 * @param url 请求路径
	 * @param params 参数
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public String doGet(String url, Map<String, String> params)
			throws ClientProtocolException, IOException, URISyntaxException {
		URIBuilder uriBuilder = new URIBuilder(url);
		for (Map.Entry<String, String> entry : params.entrySet()) {
			uriBuilder.setParameter(entry.getKey(), entry.getValue());
		}
		return this.doGet(uriBuilder.build().toString());
	}
	
	/**
	 * 不带参数的POST请求
	 * @param url 请求路径
	 * @return
	 * @throws ParseException
	 * @throws IOException
	 */
	public HttpResult doPost(String url) throws ParseException, IOException {
		return this.doPost(url, null);
	}

	/**
	 * 带有参数的POST请求
	 * @param url
	 * @param params
	 * @return
	 * @throws ParseException 
	 * @throws IOException
	 */
	public HttpResult doPost(String url, Map<String, String> params) throws ParseException, IOException {
		// 创建http POST请求
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);

		if (!CollectionUtils.isEmpty(params)) {
			List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
			for (Map.Entry<String, String> entry : params.entrySet()) {
				parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			// 构造一个form表单式的实体
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
			// 将请求实体设置到httpPost对象中
			httpPost.setEntity(formEntity);
		}

		CloseableHttpResponse response = null;
		try {
			// 执行请求获取结果
			response = this.getHttpclient().execute(httpPost);
			int statusCode = response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();

			if (Objects.isNull(entity)) {
				return new HttpResult(statusCode, null);
			}
			return new HttpResult(statusCode, EntityUtils.toString(response.getEntity(), "UTF-8"));
		} finally {
			if (response != null) {
				response.close();
			}
		}
	}
	
	private CloseableHttpClient getHttpclient() {
		return this.beanFactory.getBean(CloseableHttpClient.class);
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

}
