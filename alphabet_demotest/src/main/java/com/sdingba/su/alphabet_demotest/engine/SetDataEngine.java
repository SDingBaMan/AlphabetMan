package com.sdingba.su.alphabet_demotest.engine;

import com.sdingba.su.alphabet_demotest.bean.SetDataYan;
import com.sdingba.su.alphabet_demotest.bean.getsetdataBean;
import com.sdingba.su.alphabet_demotest.bean.senddataYanPlan;

/**
 * Created by su on 16-6-28.
 */
public interface SetDataEngine {

    /**
     * 提交设置的数据。
     * @param object
     * @return
     */
    public String sendServerData(senddataYanPlan object);

    /**
     * 获取设置的数据。
     * @param username
     * @return
     */
    public getsetdataBean initSetDateinService(String username);




}
