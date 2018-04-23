package com.hilfritz.mvp.dagger2.module;


import com.hilfritz.mvp.application.MyApplication;
import com.hilfritz.mvp.util.ImageCache;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        includes = SessionModule.class
)
public class CacheModule {
    @Singleton
    @Provides
    ImageCache provideImageCache(MyApplication myApplication){
        return new ImageCache(myApplication);
    }
}
