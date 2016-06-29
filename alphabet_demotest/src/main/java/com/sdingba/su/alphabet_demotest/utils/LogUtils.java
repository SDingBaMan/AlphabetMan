package com.sdingba.su.alphabet_demotest.utils;

import android.util.Log;

/**
 * Created by su on 16-5-29.
 */
public class LogUtils {

    // 当 测试 阶段 时 true
    private static final boolean isShow = true;

    /**
     * 功能 相当于 Log.i()的使用方法一样的。
     * @param TAG
     * @param str
     */
    public static void Logi(String TAG,String str) {
        if (isShow) {
            Log.i(TAG, str);
        }
    }

    /**
     * 功能 相当于 Log.d()的使用方法一样的。
     * @param TAG
     * @param str
     */
    public static void Logd(String TAG,String str) {
        if (isShow) {
            Log.d(TAG, str);
        }
    }

    /**
     * 功能 相当于 Log.e()的使用方法一样的。
     * @param TAG
     * @param str
     */
    public static void Loge(String TAG,String str) {
        if (isShow) {
            Log.e(TAG, str);
        }
    }





}
