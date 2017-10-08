package com.geekymax.maxschedule.database;

import org.litepal.annotation.Column;
import org.litepal.crud.DataSupport;

/**
 * Created by max on 2017/9/19.
 */

public class UserSession extends DataSupport{
    private String sessionId = null;
    private String userName = null;
    private String userPassword = null;
    private String sessionKey = null;
    @Column(unique = true)
    private String userId = null;
    public UserSession(){
    }
    public UserSession(String userId,String userPassword){
        this.userId = userId;
        this.userPassword = userPassword;
    }
    public String getSessionId() {
        return sessionId;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public void setUserName(String userName) {
        this.userName = userName;

    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
