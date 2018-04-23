package com.hilfritz.mvp.util;

import java.util.List;

/**
 * Created by hilfritz on 10/23/16.
 */

public class NumberUtil {
    /**
     * Returns the String representation of value parameter (only if less than 10)
     * ex. 1 -> 1.0, 2 -> 2.0, 9 -> 9.0, 10 -> 10, 11 -> 11
     * @param value int less than 10
     * @return String
     */
    public static String getValueWithDecimalPoints(int value){
        String retVal = "";
        if (value<10){
            retVal = value+".0";
        }
        return retVal;
    }

    /**
     * Returns the String representation of value parameter (only if less than 10)
     * ex. 1 -> 1.0, 2 -> 2.0, 9 -> 9.0, 10 -> 10.0, 11 -> 11.0
     * @param value int less than 10
     * @return String
     */
    public static String getValueWithDecimalPoints2(int value){
        String retVal = "";
        retVal = value+".0";
        return retVal;
    }

    /**
     * Converts the List<Integer> parameter to primitive int array
     * @param integers List<Integers>
     * @return int [] array
     */
    public static int[] convertToPrimitiveIntArray(List<Integer> integers)
    {
        int[] ret = new int[integers.size()];
        for (int i=0; i < ret.length; i++)
        {
            ret[i] = integers.get(i).intValue();
        }
        return ret;
    }
}
