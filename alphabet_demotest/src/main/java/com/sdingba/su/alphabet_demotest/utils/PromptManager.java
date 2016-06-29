package com.sdingba.su.alphabet_demotest.utils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.widget.Toast;

import com.sdingba.su.alphabet_demotest.R;


/**
 * 提示信息的管理
 */

public class PromptManager {
	private static ProgressDialog dialog;

	public static void showProgressDialog(Context context) {
		dialog = new ProgressDialog(context);
		dialog.setIcon(R.mipmap.ic_launcher);
		dialog.setTitle(R.string.app_name);

		dialog.setMessage("请等候，数据加载中……");
		dialog.show();
	}

	public static void closeProgressDialog() {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	/**
	 * 当判断当前手机没有网络时使用
	 *
	 * @param context
	 */
	public static void showNoNetWork(final Context context) {
		/**
		 *
		 问题：
		 android  4.0如何打开无线设置界面？
		 答案：
		 在android4.0之前可以通过下面方法打开无线网络设置页面，可是在4.0以上则会抛异常
		 Intent mIntent = new Intent("/");
		 ComponentName comp = new ComponentName(
		 "com.android.settings",
		 "com.android.settings.WirelessSettings");
		 mIntent.setComponent(comp);
		 mIntent.setAction("android.intent.action.VIEW");
		 startActivityForResult(mIntent, 0);

		 那么有什么方法进行兼容吗？
		 经过尝试采用下面的方法startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));可以兼容4.0之前及之后的版本，不过由于android3.0之后设置界面改动很大，所以个人采用下面的方法打开网络设置
		 if(android.os.Build.VERSION.SDK_INT > 10 ){
		 //3.0以上打开设置界面
		 context.startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
		 }else
		 {
		 context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
		 }
		 */


		AlertDialog.Builder builder = new Builder(context);
		builder.setIcon(R.mipmap.ic_launcher)//
				.setTitle(R.string.app_name)//
				.setMessage("当前无网络").setPositiveButton("设置", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// 跳转到系统的网络设置界面
				Intent intent = new Intent();
				intent.setClassName("com.android.settings", "com.android.settings.WirelessSettings");
				context.startActivity(intent);

			}
		}).setNegativeButton("知道了", null).show();
	}

	/**
	 * 退出系统
	 *
	 * @param context
	 */
	public static void showExitSystem(Context context) {
		AlertDialog.Builder builder = new Builder(context);
		builder.setIcon(R.mipmap.ic_launcher)//
				.setTitle(R.string.app_name)//
				.setMessage("是否退出应用").setPositiveButton("确定", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				android.os.Process.killProcess(android.os.Process.myPid());
				// 多个Activity——懒人听书：没有彻底退出应用
				// 将所有用到的Activity都存起来，获取全部，干掉
				// BaseActivity——onCreated——放到容器中
			}
		})//
				.setNegativeButton("取消", null)//
				.show();

	}

	/**
	 * 显示错误提示框
	 *
	 * @param context
	 * @param msg
	 */
	public static void showErrorDialog(Context context, String msg) {
		new AlertDialog.Builder(context)//
				.setIcon(R.mipmap.ic_launcher)//
				.setTitle(R.string.app_name)//
				.setMessage(msg)//
				.setNegativeButton("确定", null)//
				.show();
	}

	/**
	 * 吐司；
	 * @param context
	 * @param msg
     */
	public static void showToast(Context context, String msg) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

	public static void showToast(Context context, int msgResId) {
		Toast.makeText(context, msgResId, Toast.LENGTH_LONG).show();
	}

	// 当 测试 阶段 时 true
	private static final boolean isShow = true;

	/**
	 * 测试用 在正式投入市场：删
	 *
	 * @param context
	 * @param msg
	 */
	public static void showToastTest(Context context, String msg) {
		if (isShow) {
			Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
		}
	}

}
