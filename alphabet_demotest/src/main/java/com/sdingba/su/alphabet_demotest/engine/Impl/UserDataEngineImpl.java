package com.sdingba.su.alphabet_demotest.engine.Impl;

import com.alibaba.fastjson.JSON;
import com.sdingba.su.alphabet_demotest.ConstantValue;
import com.sdingba.su.alphabet_demotest.bean.Netbean.userDataDay;
import com.sdingba.su.alphabet_demotest.bean.userData;
import com.sdingba.su.alphabet_demotest.engine.UserDataEngine;
import com.sdingba.su.alphabet_demotest.net.HttpClientUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
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

    @Override
    public List<userData> pullUserDatafromWeb(String username) {
        String userId = username;

        List<userData> userDatas = null;

        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", userId);

        String str = util.sendPost(ConstantValue.huoquUserData, params);

        if (str == null) {
            return null;
        }else{
            try {
                JSONObject jsonObject = new JSONObject(str);
                String userJson = jsonObject.getString("resultUserdataList");

                userDatas = JSON.parseArray(userJson, userData.class);
//                System.out.println(userJson+" -- ---- ------ -------- --- ------"+userDatas.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


        return userDatas;
    }

    @Override
    public String[] getMaxAll(String username) {
        String [] userString=new String[3];
        String user = username;
        Map<String, String> params = new HashMap<String, String>();
        params.put("userId", user);
        String str = util.sendPost(ConstantValue.GET_MAX_ALL_Serlvet, params);

        if (str == null) {
            return null;
        }else{
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(str);
                String userJson = jsonObject.getString("sendNumber");

                if (userJson.equals("noDate")) {
                    return null;
                }
                String allAvg = jsonObject.getString("avgAll");
                String UserAvg = jsonObject.getString("avgUser");

                userString[0] = userJson;
                userString[1] = allAvg;
                userString[2] = UserAvg;



                return userString;
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


        return null;
    }
}
