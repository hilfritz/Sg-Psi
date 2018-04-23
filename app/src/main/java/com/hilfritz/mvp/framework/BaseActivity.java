package com.hilfritz.mvp.framework;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hilfritz.mvp.framework.helper.AppVisibilityInterface;

/**IMPORTANT: FRAMEWORK CLASS**/
/* Features:
 * 1. checks if app went to background, to do this your activity/presenter must implement AppVisibilityInterface.java and set it to #setAppVisibilityHandler() of the activity
 * @author Hilfritz P. Camallere on 8/20/2016.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";
    public static int sessionDepth = 0;

    AppVisibilityInterface appVisibilityHandler;

    @Override
    public void onStop() {
        super.onStop();
        if (sessionDepth > 0)
            sessionDepth--;
        if (sessionDepth == 0) {
            // app went to background
            // TODO: 17/4/17 DO YOUR BROADCAST HERE TO HANDLE
            Log.d(TAG, "onStop: [sessionDepth:"+sessionDepth+"] app went to background");

            if (getAppVisibilityHandler()!=null){
                getAppVisibilityHandler().onAppSentToBackground();
            }else{
                Log.d(TAG, "onStop: [sessionDepth:"+sessionDepth+"] app went to background, implement AppVisibilityHandler.java interface to handle when app goes to background.");

            }
        }
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onStart()");
        sessionDepth++;
        super.onResume();

    }

    public void setAppVisibilityHandler(AppVisibilityInterface appVisibilityHandler) {
        this.appVisibilityHandler = appVisibilityHandler;
    }

    public AppVisibilityInterface getAppVisibilityHandler() {
        return appVisibilityHandler;
    }

    /**
     * see http://stackoverflow.com/questions/4238921/detect-whether-there-is-an-internet-connection-available-on-android
     * @return
     */
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();

    }

}
