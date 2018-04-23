package com.hilfritz.mvp.util;

import android.util.Log;

import java.io.File;

/**
 * Created by Hilfritz Camallere on 11/1/17.
 * PC name herdmacbook1
 */

public class FileUtil {
    private static final String TAG = "FileUtil";

    /**
     * Deletes the folder and its contents
     * @param folder File
     */
    public static void deleteFolder(File folder) {

        if (folder!=null && folder.exists()) {

            File[] files = folder.listFiles();
            if (files != null) { //some JVMs return null for empty dirs
                for (File f : files) {
                    if (f.isDirectory()) {
                        deleteFolder(f);
                    } else {
                        f.delete();
                    }
                }
            }
            folder.delete();
        }else{
            Log.d(TAG, "deleteFolder: cannot delete folder, it doesn't exit or is null");
        }
    }
}
