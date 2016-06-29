package com.sdingba.su.alphabet_demotest.engine;

import com.sdingba.su.alphabet_demotest.bean.Netbean.UserLogin;
import com.sdingba.su.alphabet_demotest.bean.Netbean.UserMan;
import com.sdingba.su.alphabet_demotest.bean.friendBean;

import java.util.List;

/**
 * Created by su on 16-6-21.
 */
public interface UserEngine {

    UserMan login(UserLogin userLogin);


    void aaa();

    List<friendBean> returnFrienfList(String username);
}
