package com.hilfritz.mvp.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Hilfritz Camallere on 16/3/17.
 * PC name herdmacbook1
 */

public class ConnectionUtil {
    /**
     * see http://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
     * @return
     */
    public static final boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

}
