package com.sdingba.su.alphabet_demotest.engine.Impl;


import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.sdingba.su.alphabet_demotest.ConstantValue;
import com.sdingba.su.alphabet_demotest.bean.Netbean.UserLogin;
import com.sdingba.su.alphabet_demotest.bean.Netbean.UserMan;
import com.sdingba.su.alphabet_demotest.bean.friendBean;
import com.sdingba.su.alphabet_demotest.engine.UserEngine;
import com.sdingba.su.alphabet_demotest.net.HttpClientUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by su on 16-6-21.
 */
public class UserEngineImpl implements UserEngine {
    private static final String TAG = "UserEngineImpl";
    HttpClientUtil util = new HttpClientUtil();
    @Override
    public UserMan login(UserLogin userLogin) {


        UserMan userList = null;
        System.out.println("=========================================");
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", userLogin.getUlId());
        params.put("password", userLogin.getUlpassword());

        String json = util.sendPost(ConstantValue.Login, params);
        if (json == null) {
            return null;
        }
        Log.i(TAG, json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            String userJson = jsonObject.getString("userinfo");

            userList = JSON.parseObject(userJson, UserMan.class);
            System.out.println(userJson+" -- ---- ------ -------- --- ------"+userList.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return userList;
    }

    @Override
    public void aaa() {
        System.out.println("================+_+_+_+_+_+_+_+_+_+_+_+");
    }



    @Override
    public List<friendBean> returnFrienfList(String username) {
        List<friendBean> friendBean=null;
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);

        String json = util.sendPost(ConstantValue.FriendList, params);
        if (json == null) {
            return null;
        }
        Log.i(TAG, json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            String userJson = jsonObject.getString("friendlistdata");
            friendBean =  JSON.parseArray(userJson, friendBean.class);
            System.out.println(userJson+" -- ---- ------ -------- --- ------"+friendBean.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return friendBean;
    }
}
