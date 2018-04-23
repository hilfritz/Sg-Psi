package com.hilfritz.mvp.util;

import android.content.Context;
import android.content.res.Resources;

/**
 * Created by Hilfritz Camallere on 10/1/17.
 */

public class DisplayUtil {
    public static final int getStatusBarHeight(Context context){
        int statusBarHeight = 0;
        int resource = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resource > 0) {
            statusBarHeight = context.getResources().getDimensionPixelSize(resource);
        }
        return statusBarHeight;
    }

    public static final int getNavigationBarHeight(Context context){
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

}
