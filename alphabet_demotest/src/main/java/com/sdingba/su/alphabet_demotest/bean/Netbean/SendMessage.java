package com.sdingba.su.alphabet_demotest.bean.Netbean;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by su on 16-6-2.
 * <p>
 * 好友留言 表
 */
public class SendMessage implements Serializable {


    /**
     * SMId : auto
     */
    private int smId;

    private String reciveId;

    private String sendId;

    private String title;

    private String content;

    /**
     * tinyint 类型长度为 3
     */
    private int state;

    private Date datetime;


    public int getSmId() {
        return smId;
    }

    public void setSmId(int smId) {
        this.smId = smId;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }



}
