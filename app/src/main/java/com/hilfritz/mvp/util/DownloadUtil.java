package com.hilfritz.mvp.util;

import android.util.Log;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Hilfritz Camallere on 11/1/17.
 * PC name herdmacbook1
 * USes Square OkHttp library
 */

public class DownloadUtil {
    private static final String TAG = "DownloadUtil";

    /**
     *
     * @param responseBody okhttp3.Response
     * @param destinationFile File destination destinationFile
     * @return File the destination destinationFile, null if it was not downloaded
     */
    public static final File savePicture(okhttp3.Response responseBody, File destinationFile) {
        File retVal = null;
        InputStream inputStream = null;

        try {
            inputStream = responseBody.body().byteStream();
            FileUtils.copyInputStreamToFile(inputStream, destinationFile);
            Log.d(TAG, "savePicture() downloadFileObservable() onNext() destinationFile downloaded: path:"+destinationFile.getAbsolutePath());
            retVal = destinationFile;
        } catch (Exception e) {
            Log.d(TAG, "savePicture() downloadFileObservable() onNext() destinationFile not downloaded: "+e);
            e.printStackTrace();
        }
        return retVal;
    }

    /**
     * @see http://stackoverflow.com/questions/26689464/how-to-download-image-file-by-using-okhttpclient-in-java
     * @param url String url to download
     * @return okhttp3.Response
     */
    public static final okhttp3.Response downloadFile(String url){
        Log.d(TAG, "downloadFile: urlToDownload:"+url);
        Request request = new Request.Builder()
                .url(url)
                .build();
        OkHttpClient client = new OkHttpClient();
        okhttp3.Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }
}
