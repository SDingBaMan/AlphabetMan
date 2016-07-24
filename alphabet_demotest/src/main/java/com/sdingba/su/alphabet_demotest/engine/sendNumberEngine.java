package com.sdingba.su.alphabet_demotest.engine;

import com.sdingba.su.alphabet_demotest.bean.Netbean.sendNumber;

/**
 * Created by su on 16-7-23.
 */
public interface sendNumberEngine {
    /**
     * 赠送别人烟
     *
     * @param sendNumber
     * @return
     */
    public String setSendYanNumber(sendNumber sendNumber);

    /**
     * 获取 别人赠送给 我的 香烟
     * @param userID
     * @return
     */
    public String getSendYanNumber(String userID);
}
