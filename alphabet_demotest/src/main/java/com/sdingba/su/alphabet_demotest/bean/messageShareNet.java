package com.sdingba.su.alphabet_demotest.bean;

import java.io.Serializable;
import java.sql.Date;

/**
 * Created by su on 16-7-23.
 */

public class messageShareNet implements Serializable {

    /**
     * auto
     */
    private int msId;

    private String userId;

    private String title;

    private String content;

    private String datetime;


    public int getMsId() {
        return msId;
    }

    public void setMsId(int msId) {
        this.msId = msId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }




}