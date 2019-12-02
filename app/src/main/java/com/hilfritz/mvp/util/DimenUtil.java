package com.hilfritz.mvp.util;

import android.content.Context;
import androidx.annotation.DimenRes;

/**
 * Created by Hilfritz Camallere on 19/12/16.
 */

public class DimenUtil {
    private static final String TAG = "DimenUtil";

    /**
     * @see http://stackoverflow.com/questions/11121028/load-dimension-value-from-res-values-dimension-xml-from-source-code
     * @param context
     * @param dimenRes
     * @return
     */
    public static final int getPixelValueFromDimen(Context context, @DimenRes int dimenRes){
        return (int) context.getResources().getDimension(dimenRes);
    }

}
