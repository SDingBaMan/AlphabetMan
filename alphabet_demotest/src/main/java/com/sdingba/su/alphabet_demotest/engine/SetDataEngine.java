package com.sdingba.su.alphabet_demotest.engine;

import com.sdingba.su.alphabet_demotest.bean.SetDataYan;
import com.sdingba.su.alphabet_demotest.bean.getsetdataBean;
import com.sdingba.su.alphabet_demotest.bean.senddataYanPlan;

/**
 * Created by su on 16-6-28.
 */
public interface SetDataEngine {

    public String sendServerData(senddataYanPlan object);

    public getsetdataBean initSetDateinService(String username);
}
