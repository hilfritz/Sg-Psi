package com.hilfritz.mvp;

import android.app.Application;
import android.content.Context;

import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.io.File;

/**
 * Created by home on 7/8/2017.
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class,
        application = MyApplicationTest.class,
        sdk=22)
abstract public class AndroidTest {
    public class ApplicationStub extends Application{}
    public Context getContext(){
        return RuntimeEnvironment.application;
    }
    public File getCache(){
        return getContext().getCacheDir();
    }
}
