package com.sdingba.su.alphabet_demotest.net;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Message;

import com.sdingba.su.alphabet_demotest.utils.LogUtils;
import com.sdingba.su.alphabet_demotest.utils.PromptManager;

/**
 * Created by su on 16-5-30.
 * <p/>
 * Params : 是 用于 传递给 doInBackground(...) 参数里面的值
 * Result ： 是 返回  doInBackground 的返回值。。
 */
public abstract class MyHttpAsyncTask<Params, Result>

        extends AsyncTask<Params, Void, Result> {

    Context context;

    /**
     * 传递上下文的使用。
     *
     * @param context
     */
    public MyHttpAsyncTask(Context context) {
        this.context = context;
    }

    public final AsyncTask<Params, Void, Result> executeProxy(
            Params... params) {
        if (NetUtil.checkNetWork(context)) {
            return super.execute(params);
        } else {
            PromptManager.showNoNetWork(context);
        }
        return null;
    }


}
