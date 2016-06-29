package com.sdingba.su.alphabet_demotest.bean.Netbean;

import java.io.Serializable;

/**
 * Created by su on 16-6-2.
 */
public class UserLogin implements Serializable {

    private String ulId;

    private String ulpassword;

    public String getUlId() {
        return ulId;
    }

    public void setUlId(String ulId) {
        this.ulId = ulId;
    }

    public String getUlpassword() {
        return ulpassword;
    }

    public void setUlpassword(String ulpassword) {
        this.ulpassword = ulpassword;
    }
}
