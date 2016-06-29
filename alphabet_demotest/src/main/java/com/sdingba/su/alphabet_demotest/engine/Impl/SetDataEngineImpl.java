package com.sdingba.su.alphabet_demotest.engine.Impl;

import com.alibaba.fastjson.JSON;
import com.sdingba.su.alphabet_demotest.ConstantValue;
import com.sdingba.su.alphabet_demotest.bean.SetDataYan;
import com.sdingba.su.alphabet_demotest.bean.getsetdataBean;
import com.sdingba.su.alphabet_demotest.bean.senddataYanPlan;
import com.sdingba.su.alphabet_demotest.engine.SetDataEngine;
import com.sdingba.su.alphabet_demotest.net.HttpClientUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by su on 16-6-28.
 */
public class SetDataEngineImpl implements SetDataEngine {
    HttpClientUtil util = new HttpClientUtil();

    @Override
    public String sendServerData(senddataYanPlan object) {
        senddataYanPlan oj = object;

        Map<String, String> params = new HashMap<String, String>();
        params.put("username", oj.getUsername());
        params.put("starttime", oj.getStarttime());
        params.put("datasetSc", oj.getDatasetSc());

        String result = util.sendPost(ConstantValue.sendDataYanPlan, params);
        System.out.println(result+" lllllllll ");
        if (result == null) {
            return "";
        }

        if (result.equals("submitDateOk")) {
            return "ok";

        } else if (result.equals("submitDateError")) {
            return "error";
        }
        return "";
    }

    @Override
    public getsetdataBean initSetDateinService(String username) {

        getsetdataBean dataYan;
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);

        String reslute = util.sendPost(ConstantValue.getServierSetDataPlan, params);

        if (reslute == null) {
            return null;
        }

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(reslute);
            String userJson = jsonObject.getString("resultSetdata");
            if (userJson.equals("noDate")) {
                return null;
            }

            dataYan = JSON.parseObject(userJson, getsetdataBean.class);

            return dataYan;
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }
}
