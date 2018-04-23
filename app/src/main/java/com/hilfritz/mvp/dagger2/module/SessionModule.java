package com.hilfritz.mvp.dagger2.module;

import com.hilfritz.mvp.application.MyApplication;
import com.hilfritz.mvp.dagger2.session.SessionData;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hilfritz P. Camallere on 7/2/2016.
 */
@Module
public class SessionModule {
    MyApplication myApplication;

    public SessionModule(MyApplication myApplication) {
        this.myApplication = myApplication;
    }

    @Singleton
    @Provides
    SessionData provideSessionData(){
        return new SessionData(this.myApplication);
    }


}
