package com.sdingba.su.alphabet_demotest.engine.Impl;

import com.sdingba.su.alphabet_demotest.ConstantValue;
import com.sdingba.su.alphabet_demotest.bean.Netbean.userDataDay;
import com.sdingba.su.alphabet_demotest.engine.UserDataEngine;
import com.sdingba.su.alphabet_demotest.net.HttpClientUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by su on 16-6-24.
 */
public class UserDataEngineImpl implements UserDataEngine {
    HttpClientUtil util = new HttpClientUtil();

    @Override
    public String SendUserDayXiYanDay(userDataDay userdata) {
        userDataDay userd = userdata;
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", userd.getId());
        params.put("datatime", userd.getDatetime());
        params.put("lastYanNumber", userd.getLastYanNumber());
        params.put("yiyanNumber", userd.getYunNum());

        String str = util.sendPost(ConstantValue.SendXiYanNumberDay, params);

        if (str == null) {
            return "";
        }
        if (str.equals("errorUserData")) {
            return "error";
        } else if (str.equals("OkUserData")) {
            return "ok";
        }
        return "error";
    }
}
