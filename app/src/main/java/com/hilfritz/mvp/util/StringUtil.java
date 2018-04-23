package com.hilfritz.mvp.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hilfritz on 8/1/16.
 */

public class StringUtil {
    public static final String DAY_SUPERSCRIPT ="<sup><small>&nbsp;D</small></sup>";
    public static final String HOUR_SUPERSCRIPT ="<sup><small>&nbsp;H</small></sup>";


    /**
     * http://stackoverflow.com/questions/153716/verify-email-in-java
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email){
        boolean retVal = false;
        Pattern pattern = Pattern.compile("^.+@.+\\..+$");
        //Pattern pattern = Pattern.compile("^[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$");
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()){
            retVal = true;
        }
        return retVal;
    }

    public static boolean isStringEmpty(String str){
        boolean retVal = true;
        if(str != null && !str.isEmpty()){
            retVal = false;
        }

        return retVal;
    }

    /**
     *
     * @param toBeCapped String sample "hello there today is very nice"
     * @return String sample "Hello There Today Is Very Nice"
     */
    public static final String capitalizeFirstLettersOfWords(String toBeCapped){
        String[] tokens = toBeCapped.split("\\s");
        toBeCapped = "";

        for(int i = 0; i < tokens.length; i++){
            char capLetter = Character.toUpperCase(tokens[i].charAt(0));
            toBeCapped +=  " " + capLetter + tokens[i].substring(1);
        }
        toBeCapped = toBeCapped.trim();
        return toBeCapped;
    }

    public static final String ensureDoubleDigits(int x){
        String retVal = ""+x;
        if (x < 10){
            retVal = "0"+x;
        }
        return retVal;
    }
    public static final String makeTextHtmlRed(String str){
        return "<font color='#EE0000'>"+str+"</font>";
    }

}