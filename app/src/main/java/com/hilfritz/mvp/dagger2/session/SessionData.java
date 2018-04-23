package com.hilfritz.mvp.dagger2.session;

import com.hilfritz.mvp.application.MyApplication;

import java.util.UUID;

/**
 * Created by Hilfritz Camallere on 15/3/17.
 * PC name herdmacbook1
 */

public class SessionData {
    MyApplication myApplication;
    String sessionUuid;

    public SessionData(MyApplication myApplication) {
        this.myApplication = myApplication;
    }

    public void sessionStart(){
        sessionUuid = UUID.randomUUID().toString()+"<>"+System.currentTimeMillis();
    }

    public void sessionEnd(){
        sessionUuid = "";
    }

    public String getSessionUuid() {
        return sessionUuid;
    }

    public void setSessionUuid(String sessionUuid) {
        this.sessionUuid = sessionUuid;
    }

    public MyApplication getMyApplication() {
        return myApplication;
    }

    public void setMyApplication(MyApplication myApplication) {
        this.myApplication = myApplication;
    }

}
