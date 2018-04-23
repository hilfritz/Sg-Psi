package com.hilfritz.mvp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.filters.MediumTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Created by Hilfritz Camallere on 17/7/17.
 */
@MediumTest
@RunWith(AndroidJUnit4.class)
public class BaseInstrumentationTest2 {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.hilfritz.mvp", appContext.getPackageName());
    }

    public void sleep(long ms){
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
