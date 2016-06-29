package com.sdingba.su.alphabet_demotest.bean.Netbean;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by su on 16-6-2.
 *
 * 发送 好友消息 表
 *
 */
public class Sendqq implements Serializable {

    /**
     * SQId 是 auto
     */
    private int sqId;

    private String reciveId;

    private String sendId;

    private String title;

    private Date datetime;


    public int getSqId() {
        return sqId;
    }

    public void setSqId(int sqId) {
        this.sqId = sqId;
    }

    public String getReciveId() {
        return reciveId;
    }

    public void setReciveId(String reciveId) {
        this.reciveId = reciveId;
    }

    public String getSendId() {
        return sendId;
    }

    public void setSendId(String sendId) {
        this.sendId = sendId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }







}
