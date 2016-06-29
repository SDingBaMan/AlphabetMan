package com.sdingba.su.alphabet_demotest.bean.Netbean;

import java.io.Serializable;

/**
 * Created by su on 16-6-2.
 */
public class FriendUser implements Serializable {

    /**
     * FId ： 自增长；不需要理会；
     */
    private int fid;

    private String userId;

    private String friendId;


    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }



}
