package com.hilfritz.mvp.util.logging;

import android.os.Environment;
import android.util.Log;

import com.hilfritz.mvp.application.MyApplication;

import java.io.File;

/**
 * Created by Hilfritz Camallere on 27/3/17.
 * PC name herdmacbook1
 */

public class LogFileManager {

    public static final String LOG_DIR = "logs";
    private static final String TAG = "LogFileManager";
    private static final String MSG_FORMAT = "%s: %s - %s";  // timestamp, tag, message

    private static int FILE_SIZE_LIMIT = 1000;                  // bytes

    File logFilesDir;
    File currentLogfile;

    MyApplication myApplication;

    public LogFileManager(MyApplication myApplication) {
        //myApplication.getAppComponent().inject(this);
        this.myApplication = myApplication;
        initializeDirectories(myApplication);

    }

    void initializeDirectories(MyApplication myApplication){
        File externalStoragePublicDirectory = myApplication.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS); //this directory will be deleted when the app is uninstalled
        logFilesDir = new File(externalStoragePublicDirectory, LOG_DIR);
        //logFilesDir = new File("temp", LOG_DIR);
        if (logFilesDir.exists()==false){
            if (logFilesDir.mkdirs()){
                Log.d(TAG, "initializeDirectories(): logfiles directory created "+logFilesDir.getAbsolutePath());
            }else{
                Log.d(TAG, "initializeDirectories(): logfiles directory cannot be created");
            }
        }
    }

    public File getLogFilesDir() {
        return logFilesDir;
    }

    public void setLogFilesDir(File logFilesDir) {
        this.logFilesDir = logFilesDir;
    }

    public File getCurrentLogFile(){
        initializeDirectories(myApplication);
        String currentTimestamp = FileLogWriter.getCurrentTimeStamp();
        String dateStamp = FileLogWriter.getCurrentDateStamp();
        currentLogfile = new File(getLogFilesDir(), dateStamp);

        try {
            //CHECK IF FILE IS EXISTING
            if (currentLogfile!=null && currentLogfile.exists()) {
                //CHECK IF FILE SIZE EXCEEDED

                if (currentLogfile.length() > FILE_SIZE_LIMIT) {
                    System.out.println("getCurrentLogFile: existing and size exceeding");
                    //Timber.d("getCurrentLogFile: existing and size exceeding");
                    //IF EXCEEDED, ADD TIMESTAMP AND ADD ".old" to the file then create a new one
                    File to = new File(currentLogfile.getAbsolutePath() + "_"+currentTimestamp+".old");
                    if (to.exists()) {
                        to.delete();
                    }
                    currentLogfile.renameTo(to);
                    currentLogfile = new File(getLogFilesDir(), dateStamp);
                    currentLogfile.createNewFile();
                } else {
                    //Timber.d("getCurrentLogFile: existing and size still okay");

                    System.out.println("getCurrentLogFile: existing and size still okay");
                }
            }else{
                //IF NOT EXISTING, CREATE THE LOG FILE IN LOG FILES DIRECTORY
                System.out.println("getCurrentLogFile: not existing and creating new file");
                currentLogfile = new File(getLogFilesDir(), dateStamp);
                currentLogfile.createNewFile();
                System.out.println("getCurrentLogFile: currentLogfileSize:"+currentLogfile.length());

            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return currentLogfile;
    }



    private boolean isExceedFileSizeLimit(File sTheLogFile){
        boolean createdNewLogFile = false;
        System.out.println("isExceedFileSizeLimit: fileSize:"+sTheLogFile.length()+" limit:"+FILE_SIZE_LIMIT);

        if ( sTheLogFile.length() > FILE_SIZE_LIMIT)
        {
            createdNewLogFile = true;
        }
        return createdNewLogFile;
    }



}
