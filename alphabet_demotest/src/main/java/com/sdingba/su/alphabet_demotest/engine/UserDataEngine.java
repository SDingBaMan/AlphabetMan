package com.sdingba.su.alphabet_demotest.engine;

import com.sdingba.su.alphabet_demotest.bean.Netbean.userDataDay;
import com.sdingba.su.alphabet_demotest.bean.userData;

import java.util.List;

/**
 * Created by su on 16-6-24.
 */
public interface UserDataEngine {
    /**
     * 向 服务器 发送 今日 用户吸烟的数据
     *
     * @param userdata
     * @return
     */
    public String SendUserDayXiYanDay(userDataDay userdata);

    public List<userData> pullUserDatafromWeb(String username);

    public String[] getMaxAll(String username);
}
