package com.sdingba.su.alphabet_demotest.net;

import com.sdingba.su.alphabet_demotest.GlobalParams;
import com.sdingba.su.alphabet_demotest.utils.LogUtils;


import android.annotation.TargetApi;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

public class NetUtil {

	private static final String TAG = "NetUtil";
	// 4.0模拟器屏蔽掉该权限
	private static Uri PREFERRED_APN_URI =
			Uri.parse("content://telephony/carriers/preferapn");
	/**
	 * 检查网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNetWork(Context context) {
		// ConnectivityManager//系统服务
		// ①判断WIFI联网情况
		boolean isWifi = isWifi(context);
		// ②判断MOBILE联网情况
		boolean isMobile = isMobile(context);

		if (!isWifi && !isMobile) {
			// 如果都不能联网，提示用户
			return false;
		}

		// ③判断MOBILE是否连接
		if (isMobile) {
			// 如果是，判断一下是否是wap（代理的信息）
			// wap 还是 net？看当前正在连接的去渠道的配置信息（proxy port），如果有值wap
			readAPN(context);
			LogUtils.Logi(TAG, "判断MOBILE是否连接,");
			LogUtils.Logi(TAG, "4.0模拟器屏蔽掉该权限,");
			LogUtils.Logi(TAG, "检测 是否 是 非无线联网的 事情。");
		}
		return true;
	}

	/**
	 * 读取APN列表中，当前正在处于连接的配置信息（单选）
	 */
	private static void readAPN(Context context) {
		// 读取联系人
		ContentResolver resolver = context.getContentResolver();
		
		// uri : 不能获取所有的apn列表信息，获取当前被选中
		Cursor query = resolver.query(PREFERRED_APN_URI, null, null, null, null);
		

		if (query != null && query.moveToFirst()) {
			// proxy
			// port
			GlobalParams.PROXY_IP = query.getString(query.getColumnIndex("proxy"));
			GlobalParams.PROXY_PORT = query.getInt(query.getColumnIndex("port"));
			LogUtils.Logi(TAG, "ip 是 ： " + GlobalParams.PROXY_IP + "----" + "port  ：  " + GlobalParams.PROXY_PORT);

		}

	}

	/**
	 * 判断wifi是否处于连接状态
	 */

	public static boolean isWifi(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		// NetworkInfo:支持WIFI和MOBILE
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (networkInfo != null) {
			return networkInfo.isConnected();
		}
		return false;
	}

	/**
	 * 判断Mobile是否处于连接状态
	 */
	public static boolean isMobile(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		// NetworkInfo:支持WIFI和MOBILE
		NetworkInfo networkInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
		if (networkInfo != null) {
			return networkInfo.isConnected();
		}
		return false;
	}
}
