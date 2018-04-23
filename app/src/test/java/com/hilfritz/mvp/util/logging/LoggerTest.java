package com.hilfritz.mvp.util.logging;

import android.util.Log;

import com.hilfritz.mvp.BuildConfig;
import com.hilfritz.mvp.MyApplicationTest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Hilfritz Camallere on 27/3/17.
 * PC name herdmacbook1
 */

@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, application = MyApplicationTest.class)
public class LoggerTest {
    TimberFileLogTree timberFileLogTree;
    LogFileManager logFileManager;
    //@Before
    public void setup(){
        logFileManager = new LogFileManager(null);
        timberFileLogTree = new TimberFileLogTree(logFileManager);



    }

    @Test
    public void shouldPass(){
        assertTrue(1==1);
    }

    //@Test
    public void testLoggingToFile(){
        int size = 5000;
        for (int i = 0; i < size; i++) {
            timberFileLogTree.log(Log.DEBUG, "testLoggingToFile", "message >"+i);
        }

    }

}