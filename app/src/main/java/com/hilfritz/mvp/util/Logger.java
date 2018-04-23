package com.hilfritz.mvp.util;

import android.util.Log;

/**
 * Created by hilfritz on 30/3/16.
 * @see http://stackoverflow.com/questions/7606077/how-to-display-long-messages-in-logcat
 * this logger class helps prevent string being cut when string is too long
 */
public class Logger {
    public static final String API_REQUEST = "#API-REQUEST";
    public static final String SESSION_DATA = "#SESSION-DATA";

    public static void log(int priority, String TAG, String message) {
        int maxLogSize = 1000;
        for(int i = 0; i <= message.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
            end = end > message.length() ? message.length() : end;

            final String msg = message.substring(start, end);
            switch (priority) {
                case Log.DEBUG:
                    Log.d(TAG, msg);
                    break;
                case Log.INFO:
                    Log.i(TAG, msg);
                    break;
                case Log.WARN:
                    Log.w(TAG, msg);
                    break;
                case Log.ERROR:
                    Log.e(TAG, msg);
                    break;
            }
        }
    }

    /**
     * This one sends log to console and to file
     * @param priority
     * @param TAG
     * @param message
     */
    public static void logf(int priority, String TAG, String message) {
        int maxLogSize = 1000;
        for(int i = 0; i <= message.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
            end = end > message.length() ? message.length() : end;

            final String msg = message.substring(start, end);
            switch (priority) {
                case Log.DEBUG:
                    Log.d(TAG, msg);
                    break;
                case Log.INFO:
                    Log.i(TAG, msg);
                    break;
                case Log.WARN:
                    Log.w(TAG, msg);
                    break;
                case Log.ERROR:
                    Log.e(TAG, msg);
                    break;
            }
        }
    }


}
