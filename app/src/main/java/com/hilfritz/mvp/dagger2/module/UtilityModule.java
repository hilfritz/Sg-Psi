package com.hilfritz.mvp.dagger2.module;

import com.google.gson.Gson;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hilfritz P. Camallere on 7/2/2016.
 */
@Module
public class UtilityModule {
    @Provides
    @Singleton
    Gson providesGson(){
        return new Gson();
    }

}
