package com.hilfritz.mvp.util;

import android.os.Environment;
import android.util.Log;

import com.hilfritz.mvp.application.MyApplication;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created by Hilfritz Camallere on 21/3/16.
 */
public class ImageCache {
    private static final String TAG = "ImageCache";
    public static final java.lang.String DIRECTORY_IMAGE_CACHE_DIR = "image";
    public static final java.lang.String REPORTING_OFFICER_IMAGE_CACHE_DIR = "reportingOfficer";
    public static final java.lang.String PAYSLIP_DIR = ".payslip";
    File cacheDir;
    File imageDir;
    File reportingOfficerDir;
    File payslipDir;
    // Singleton instance
    private static ImageCache sInstance = null;
    MyApplication myApplication;


    public static ImageCache getInstance() {
        return sInstance;
    }

    public ImageCache(MyApplication myApplication){
        this.myApplication = myApplication;
        sInstance = this;
        initializeDirectories(myApplication);
    }

    public void initializeDirectories(MyApplication myApplication){
        //cacheDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        //cacheDir = Environment.getExternalStorageDirectory().getAbsoluteFile();
        File filesDir = myApplication.getFilesDir();

        //File cacheDir = MyApplication.getInstance().getDir("HerdMe", Context.MODE_PRIVATE);
        File cacheDir = new File(filesDir, "HerdMe");


        //File externalStoragePublicDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        //File externalStoragePublicDirectory = Environment.getDownloadCacheDirectory();
        //File externalStoragePublicDirectory = Environment.getDataDirectory(); //NOT WORKING
        File externalStoragePublicDirectory = myApplication.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS); //this directory will be deleted when the app is uninstalled
        //File externalStoragePublicDirectory = filesDir;

        File external = new File(externalStoragePublicDirectory, "HerdMe");

        if (external.exists()==false){
            if (external.mkdirs()){
                Log.d(TAG, "initializeDirectories(): external directory created");
            }else{
                Log.d(TAG, "initializeDirectories(): external directory cannot be created");
            }
        }

        if (cacheDir.exists()==false){
            if (cacheDir.mkdirs()){
                Log.d(TAG, "initializeDirectories(): cache directory created");
            }else{
                Log.d(TAG, "initializeDirectories(): cache directory cannot be created");
            }
        }

        imageDir = new File(cacheDir, DIRECTORY_IMAGE_CACHE_DIR);
        if (imageDir.exists()==false){
            if (imageDir.mkdirs()){
                Log.d(TAG, "initializeDirectories(): imageDir directory created");
            }else{
                Log.d(TAG, "initializeDirectories(): imageDir directory cannot be created");
            }
        }

        reportingOfficerDir = new File(imageDir, REPORTING_OFFICER_IMAGE_CACHE_DIR);
        if (reportingOfficerDir.exists()==false){
            if (reportingOfficerDir.mkdirs()){
                Log.d(TAG, "initializeDirectories(): reportingOfficerDir directory created");
            }else{
                Log.d(TAG, "initializeDirectories(): reportingOfficerDir directory cannot be created");
            }
        }

        payslipDir = new File(external, PAYSLIP_DIR);
        if (payslipDir.exists()==false){
            if (payslipDir.mkdirs()){
                Log.d(TAG, "initializeDirectories(): payslip directory created");
            }else{
                Log.d(TAG, "initializeDirectories(): payslip directory cannot be created");
            }
        }
    }



    public File getReportingOfficerCacheDirectory(MyApplication myApplication){
        initializeDirectories(myApplication);
        return reportingOfficerDir;
    }


    public File getPayslipDir(MyApplication myApplication) {
        initializeDirectories(myApplication);
        return payslipDir;
    }

    public void setPayslipDir(File payslipDir) {
        this.payslipDir = payslipDir;
    }

    public void reset() {
        try {
            FileUtils.deleteDirectory(payslipDir);
            FileUtils.deleteDirectory(imageDir);
            FileUtils.deleteDirectory(reportingOfficerDir);
            //initializeDirectories();
        } catch (IOException e) {
            Log.d(TAG, "reset: exception:"+e);
            e.printStackTrace();
        }

    }

}
