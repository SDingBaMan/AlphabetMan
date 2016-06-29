package com.sdingba.su.alphabet_demotest.bean;

/**
 * Created by su on 16-4-29.
 */
public class NewInfo {
    private String title;
    private String detail;
    private Integer comment;
    private String imageUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getComment() {
        return comment;
    }

    public void setComment(Integer comment) {
        this.comment = comment;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    @Override
    public String toString() {
        return "NewInfo [title=" + title + ", detail=" + detail + ", comment="
                + comment + ", imageUrl=" + imageUrl + "]";
    }


}
