package com.sdingba.su.alphabet_demotest.bean.Netbean;

import java.io.Serializable;

/**
 * Created by su on 16-6-2.
 */
public class UserMan implements Serializable {

    /**
     * 最重要的主键
     */
    private String umId;

    private String username;

    private String nickName;

    private String phone;

    private String mail;

    private String age;

    private int sex;

    /**
     * 激活
     */
    private int state;

    /**
     * 管理员
     */
    private int fifter;

    public String getUmId() {
        return umId;
    }

    public void setUmId(String umId) {
        this.umId = umId;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getFifter() {
        return fifter;
    }

    public void setFifter(int fifter) {
        this.fifter = fifter;
    }


}
