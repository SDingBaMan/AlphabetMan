package com.sdingba.su.alphabet_demotest.bean.Netbean;


import java.io.Serializable;
import java.sql.Date;

/**
 * Created by su on 16-6-2.
 */
public class News  implements Serializable {

    /**
     * NId ： 是 auto
     */
    private int nid;

    private String title;

    private String content;

    private Date datetime;


    public int getNid() {
        return nid;
    }

    public void setNid(int nid) {
        this.nid = nid;
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

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }


}
