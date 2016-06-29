package com.sdingba.su.alphabet_demotest.utils;

import com.sdingba.su.alphabet_demotest.R;

import org.apache.http.util.EncodingUtils;

import java.io.FileReader;
import java.io.InputStream;
import java.util.Properties;

/**
 * 工厂类
 * @author Administrator
 *
 */
public class BeanFactory {
	// 依据配置文件加载实例
	private static Properties properties;
	static{
		properties=new Properties();
		// bean.properties必须在src的跟目录下
		try {
			System.out.println("================");
//			System.out.println(new FileReader(
//					BeanFactory.class.getClassLoader().getResource("bean.properties").getPath()
//			));
//
//			properties.load(
//						new FileReader(
//							BeanFactory.class.getClassLoader().getResource("bean.properties").getPath()
//						));

//			InputStream is = BeanFactory.class.getClassLoader().getResourceAsStream("/bean.properties");
//
			properties.load(BeanFactory.class.getClassLoader().getResourceAsStream("bean.properties"));




		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 加载需要的实现类
	 * @param clazz
	 * @return
	 */
	public static<T> T getImpl(Class<T> clazz)
	{
		String key=clazz.getSimpleName();//clazz.getName()
		String className = properties.getProperty(key);

		try {
			return (T) Class.forName(className).newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
