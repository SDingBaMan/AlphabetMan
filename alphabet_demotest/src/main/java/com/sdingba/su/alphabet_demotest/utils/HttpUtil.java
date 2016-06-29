package com.sdingba.su.alphabet_demotest.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
/**
 * 链接服务器工具

 */
public class HttpUtil {
	   /**
	    * 链接服务器 工具
	    * 
	    * @param urlStr 请求地址url
	    * @param requestMethod 请求方式“GET”/"POST"
	    * @return 字符串格式的json信息
	    */
	    public static String getJsonContent(String urlStr ,String requestMethod)
	    {
	        try
	        {// 获取HttpURLConnection连接对象
	            URL url = new URL(urlStr);
	            HttpURLConnection httpConn = (HttpURLConnection) url
	                    .openConnection();
	            // 设置连接属性
	            httpConn.setConnectTimeout(3000);
	            httpConn.setDoInput(true);
	            httpConn.setRequestMethod(requestMethod);
	            // 获取相应码
	            int respCode = httpConn.getResponseCode();
	            if (respCode == 200)
	            {
	                return ConvertStream2Json(httpConn.getInputStream());
	            }
	        }
	        catch (MalformedURLException e)
	        {
	            e.printStackTrace();
	        }
	        catch (IOException e)
	        {
	            e.printStackTrace();
	        }
	        return "";
	    }

	   /**
	    * 将输入流格式的json 转换成字符串返回
		* 输入流  返回  字符串
	    * @param inputStream
	    * @return
	    */
	    private static String ConvertStream2Json(InputStream inputStream)
	    {
	        String jsonStr = "";
	        // ByteArrayOutputStream相当于内存输出流
	        ByteArrayOutputStream out = new ByteArrayOutputStream();
	        byte[] buffer = new byte[1024];
	        int len = 0;
	        // 将输入流转移到内存输出流中
	        try
	        {
	            while ((len = inputStream.read(buffer, 0, buffer.length)) != -1)
	            {
	                out.write(buffer, 0, len);
	            }
	            // 将内存流转换为字符串
	            jsonStr = new String(out.toByteArray());
	        }
	        catch (IOException e)
	        {

	            e.printStackTrace();
	        }
	        return jsonStr;
	    }
	}

