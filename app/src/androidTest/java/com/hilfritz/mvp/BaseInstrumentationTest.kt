package com.hilfritz.mvp

import android.support.test.InstrumentationRegistry
import android.support.test.filters.MediumTest
import android.support.test.runner.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by Hilfritz Camallere on 17/7/17.
 *
 */
@MediumTest
@RunWith(AndroidJUnit4::class)
class BaseInstrumentationTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()

        assertEquals("com.hilfritz.mvp", appContext.packageName)
    }
}