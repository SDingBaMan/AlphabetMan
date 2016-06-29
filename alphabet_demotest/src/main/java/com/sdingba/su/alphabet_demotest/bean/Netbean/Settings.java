package com.sdingba.su.alphabet_demotest.bean.Netbean;

import java.io.Serializable;

/**
 * Created by su on 16-6-2.
 */
public class Settings implements Serializable {

    /**
     * SId   主键    不是 auto
     */
    private String sid;    //主键

    private String userId;

    /**
     * 吸烟时间（总共的时间）
     * 
     */
    private String timeNum;

//    /**
//     * 设置的次数。
//     */
//    private String setNumber;       // TODO: 16-6-2 删除 数据库 这一行


    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTimeNum() {
        return timeNum;
    }

    public void setTimeNum(String timeNum) {
        this.timeNum = timeNum;
    }
}
