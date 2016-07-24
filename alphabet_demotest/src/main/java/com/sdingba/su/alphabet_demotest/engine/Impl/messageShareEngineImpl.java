package com.sdingba.su.alphabet_demotest.engine.Impl;

import com.alibaba.fastjson.JSON;
import com.sdingba.su.alphabet_demotest.ConstantValue;
import com.sdingba.su.alphabet_demotest.bean.messageSend;
import com.sdingba.su.alphabet_demotest.bean.messageShareNet;
import com.sdingba.su.alphabet_demotest.engine.messageShareEngine;
import com.sdingba.su.alphabet_demotest.net.HttpClientUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by su on 16-7-23.
 */
public class messageShareEngineImpl implements messageShareEngine {
    HttpClientUtil util = new HttpClientUtil();

    @Override
    public List<messageShareNet> getListMessageShare() {

        List<messageShareNet> lists = null;
        String result = util.sendPost(ConstantValue.GET_MessageShare_Serlvet, null);

        if (result == null) {
            return null;
        }

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(result);
            String json = jsonObject.getString("resultMessageShare");

            lists = JSON.parseArray(json, messageShareNet.class);


        } catch (JSONException e) {
            e.printStackTrace();
        }



        return lists;
    }

    @Override
    public String setMessageSahreToWEB(messageSend messageSend) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", messageSend.getUserId());
        params.put("title", messageSend.getTitle());
        params.put("context", messageSend.getContext());

        String result = util.sendPost(ConstantValue.SET_MessageShare_Serlvet, params);
        if (result == null) {
            return null;
        }




        return result;
    }
}
