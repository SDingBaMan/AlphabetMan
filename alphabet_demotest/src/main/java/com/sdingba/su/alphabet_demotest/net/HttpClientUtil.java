package com.sdingba.su.alphabet_demotest.net;

import android.widget.Toast;

import com.sdingba.su.alphabet_demotest.ConstantValue;
import com.sdingba.su.alphabet_demotest.GlobalParams;
import com.sdingba.su.alphabet_demotest.MainActivity;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

//import com.wwkj.redbaby.ConstantValue;
//import com.wwkj.redbaby.GloableParameters;
//import com.wwkj.redbaby.utils.PromptManager;


public class HttpClientUtil {
	private HttpClient client;

	private HttpGet get;
	private HttpPost post;

	private HttpResponse response;

	private static Header[] headers;
	static {
		headers = new BasicHeader[10];
		headers[0] = new BasicHeader("Appkey", "12343");
		headers[1] = new BasicHeader("Udid", "");// 手机串号
		headers[2] = new BasicHeader("Os", "android");//
		headers[3] = new BasicHeader("Osversion", "");//
		headers[4] = new BasicHeader("Appversion", "");// 1.0
		headers[5] = new BasicHeader("Sourceid", "");//
		headers[6] = new BasicHeader("Ver", "");

		headers[7] = new BasicHeader("Userid", "");
		headers[8] = new BasicHeader("Usersession", "");

		headers[9] = new BasicHeader("Unique", "");
	}

	public HttpClientUtil() {
		client = new DefaultHttpClient();
		if (StringUtils.isNotBlank(GlobalParams.PROXY_IP)) {
			// wap
			// 设置代理的ip和端口
			HttpHost host = new HttpHost(GlobalParams.PROXY_IP, GlobalParams.PROXY_PORT);
			// 设置代理信息
			client.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, host);// map "http.route.default-proxy"
		}
	}

	/**
	 * 发送xml文件到服务器端
	 * 
	 * @param url
	 * @param xml
	 */
	public InputStream sendXml(String url, String xml) {
		post = new HttpPost(url);
		// 设置网络超时

		try {
			StringEntity entity = new StringEntity(xml, ConstantValue.ENCODING);
			post.setEntity(entity);
			response = client.execute(post);
			if (response.getStatusLine().getStatusCode() == 200) {
				return response.getEntity().getContent();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	/**
	 * 发送post请求
	 * 
	 * @param uri
	 * @param params
	 *            请求携带参数
	 * @return  json格式的字符串
	 */
	public String sendPost(String uri, Map<String,String> params) {
		post = new HttpPost(uri);
		post.setHeaders(headers);
   
		// 处理超时
		HttpParams httpParams = new BasicHttpParams();//
//		httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 3000);
		HttpConnectionParams.setSoTimeout(httpParams, 3000);
		post.setParams(httpParams);

		
    
		// 设置参数
		if (params != null && params.size() > 0) {
			List<BasicNameValuePair> parameters = new ArrayList<BasicNameValuePair>();
			for (Entry<String, String> item : params.entrySet()) {
				BasicNameValuePair pair = new BasicNameValuePair(item.getKey(),item.getValue());
				parameters.add(pair);
			}

			try {
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(parameters, ConstantValue.ENCODING);
				post.setEntity(entity);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		try {
			response = client.execute(post);
			System.out.println(response.getStatusLine().getStatusCode()+"fffff");
			if (response.getStatusLine().getStatusCode() == 200) {
				return EntityUtils.toString(response.getEntity(),ConstantValue.ENCODING);
			}else if (response.getStatusLine().getStatusCode() == 404){

			}else if (response.getStatusLine().getStatusCode() == 500){

			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;

	}
	
	/**
	 * 发送get请求
	 * 
	 * @param uri

	 * @return
	 */
	public String sendGet(String uri) {
		get = new HttpGet(uri);
		get.setHeaders(headers);

		// 处理超时
		HttpParams httpParams = new BasicHttpParams();
		httpParams = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, 8000);
		HttpConnectionParams.setSoTimeout(httpParams, 8000);
		get.setParams(httpParams);

		try {
			response = client.execute(get);
			if (response.getStatusLine().getStatusCode() == 200) {
				// 把响应体数据变为字符串返回
				return EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}
	
	
	public boolean checkResponse(JSONObject jsonObject) {
		/*
		 * { "response": "error", "error": { "text": "用户名不存在" } }
		 */
		try {
			if ("error".equals(jsonObject.getString("response"))) {
				return false;
			}
		} catch (Exception e) {
			//e.printStackTrace();
		}
		return true;
	}
}
