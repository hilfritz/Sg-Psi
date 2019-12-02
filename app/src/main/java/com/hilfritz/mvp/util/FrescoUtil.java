package com.hilfritz.mvp.util;

import android.net.Uri;

import androidx.annotation.DrawableRes;

import com.facebook.common.util.UriUtil;

/**
 * Created by Hilfritz Camallere on 14/12/16.
 */

public class FrescoUtil {
    public static final String TAG = "FrescoUtil";
    /**
     * see http://stackoverflow.com/questions/30887615/loading-drawable-image-resource-in-frescos-simpledraweeview
     * @param drawableId
     * @return
     */
    public static final Uri getUriFromDrawableId(@DrawableRes int drawableId){
        return new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME) // "res"
                .path(String.valueOf(drawableId))
                .build();
    }

   
}
