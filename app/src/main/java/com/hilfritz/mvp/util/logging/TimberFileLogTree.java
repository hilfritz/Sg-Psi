package com.hilfritz.mvp.util.logging;

import android.os.Debug;
import android.util.Log;

import java.io.File;

import timber.log.Timber;

/**
 * Created by Hilfritz Camallere on 27/2/17.
 * PC name herdmacbook1
 * @see http://www.sureshjoshi.com/mobile/file-logging-in-android-with-timber/
 * @see https://github.com/volkerv/FileLog/blob/master/FileLog.java
 */

public class TimberFileLogTree  extends Timber.DebugTree{
    LogFileManager logFileManager;
    File dir;


    public TimberFileLogTree(LogFileManager logFileManager) {
        this.logFileManager = logFileManager;
        FileLogWriter.open(logFileManager.getCurrentLogFile(), 0);
    }

    public LogFileManager getLogFileManager() {
        return logFileManager;
    }

    public void setLogFileManager(LogFileManager logFileManager) {
        this.logFileManager = logFileManager;
    }

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
        if (priority == Log.VERBOSE) {
            return;
        }
        //String logMessage = tag + ": " + message;
        switch (priority) {
            case Log.DEBUG:
                if (t==null)
                    FileLogWriter.d(tag, message);
                else
                    FileLogWriter.d(tag, message, t);

                //mLogger.debug(logMessage);
                break;
            case Log.INFO:
                FileLogWriter.i(tag, message);

                //mLogger.info(logMessage);
                break;
            case Log.WARN:
                if (t==null)
                    FileLogWriter.w(tag, message);
                else
                    FileLogWriter.w(tag, message, t);
                //mLogger.warn(logMessage);
                break;
            case Log.ERROR:
                if (t==null)
                    FileLogWriter.e(tag, message);
                else
                    FileLogWriter.e(tag, message, t);

                //mLogger.error(logMessage);
                break;
        }
    }

    protected void log(int priority, String tag, String message) {
        if (priority == Log.VERBOSE) {
            return;
        }
        //String logMessage = tag + ": " + message;
        switch (priority) {
            case Log.DEBUG:
                FileLogWriter.d(tag, message);

                //mLogger.debug(logMessage);
                break;
            case Log.INFO:
                FileLogWriter.i(tag, message);

                //mLogger.info(logMessage);
                break;
            case Log.WARN:
                FileLogWriter.w(tag, message);
                break;
            case Log.ERROR:
                FileLogWriter.e(tag, message);
                //mLogger.error(logMessage);
                break;
        }
    }
}
