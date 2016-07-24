package com.sdingba.su.alphabet_demotest.engine;

import com.sdingba.su.alphabet_demotest.bean.messageSend;
import com.sdingba.su.alphabet_demotest.bean.messageShareNet;

import java.util.List;

/**
 * Created by su on 16-7-23.
 */
public interface messageShareEngine {

    /**
     * 获取 分享 信息
     * @return
     */
    public List<messageShareNet> getListMessageShare();

    public String setMessageSahreToWEB(messageSend ss);
}
