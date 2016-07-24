package com.sdingba.su.alphabet_demotest.bean.Netbean;

import java.sql.Date;

/**
 * Created by su on 16-7-23.
 */
public class sendNumber {
    private String snId;

    private String reciveId;
    private String sendId;
    private int uumber;
    private Date datetime;

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public int getUumber() {
        return uumber;
    }

    public void setUumber(int uumber) {
        this.uumber = uumber;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getReciveId() {
        return reciveId;
    }

    public void setReciveId(String reciveId) {
        this.reciveId = reciveId;
    }

    public String getSnId() {
        return snId;
    }

    public void setSnId(String snId) {
        this.snId = snId;
    }



}
