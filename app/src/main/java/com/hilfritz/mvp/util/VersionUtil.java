package com.hilfritz.mvp.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by Hilfritz Camallere on 14/3/17.
 * PC name herdmacbook1
 */

public class VersionUtil {
    /**
     *
     * @param activity
     * @return String[] [0] - version code, [1] version name, both are from build.gradle file
     */
    public static String[] getVersion(Context activity){
        final String[] versionCodeName = {"", ""};
        //SHOW THE VERSION
        PackageManager manager = activity.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(activity.getPackageName(), 0);
            String versionName = info.versionName;
            String versionCode = info.versionCode+"";
            versionCodeName[0]=versionCode;
            versionCodeName[1]=versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCodeName;
    }
}
