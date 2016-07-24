package com.sdingba.su.alphabet_demotest.engine.Impl;

import com.sdingba.su.alphabet_demotest.ConstantValue;
import com.sdingba.su.alphabet_demotest.bean.Netbean.sendNumber;
import com.sdingba.su.alphabet_demotest.engine.sendNumberEngine;
import com.sdingba.su.alphabet_demotest.net.HttpClientUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by su on 16-7-23.
 */
public class sendNumberEngineImpl implements sendNumberEngine {
    HttpClientUtil util = new HttpClientUtil();

    @Override
    public String setSendYanNumber(sendNumber sendNumber) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("reciveId", sendNumber.getReciveId());
        params.put("sendId", sendNumber.getSendId());
        params.put("uumber", String.valueOf(sendNumber.getUumber()));

        String result = util.sendPost(ConstantValue.SET_SendNumberYan_Serlvet, params);
        if (result == null) {
            return null;
        }
//        JSONObject jsonObject = null;
//        try {
//            jsonObject = new JSONObject(result);
//            String jsonNumber = jsonObject.getString("sendNumber");
//            return jsonNumber;
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }

        return result;
    }

    @Override
    public String getSendYanNumber(String userID) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", userID);


        String result = util.sendPost(ConstantValue.GET_SendNumberYan_Serlvet, params);
        if (result == null) {
            return null;
        }
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(result);
            String jsonNumber = jsonObject.getString("sendNumber");
            return jsonNumber;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return result;
    }
}
