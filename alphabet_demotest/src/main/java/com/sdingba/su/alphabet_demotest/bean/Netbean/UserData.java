package com.sdingba.su.alphabet_demotest.bean.Netbean;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by su on 16-6-2.
 */
public class UserData implements Serializable {

    /**
     * auto
     */
    private int udId;

    private String userId;

    /**
     * 可以吸烟次数
     */
    private int dataNumber;

    /**
     * 剩余
     */
    private int surplusNumber;

    private String datetime;


    public int getUdId() {
        return udId;
    }

    public void setUdId(int udId) {
        this.udId = udId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getDataNumber() {
        return dataNumber;
    }

    public void setDataNumber(int dataNumber) {
        this.dataNumber = dataNumber;
    }

    public int getSurplusNumber() {
        return surplusNumber;
    }

    public void setSurplusNumber(int surplusNumber) {
        this.surplusNumber = surplusNumber;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
}
