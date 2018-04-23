package com.hilfritz.mvp.api;

/**
 * Created by Hilfritz Camallere on 15/3/17.
 * PC name herdmacbook1
 */

import android.util.Log;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;

/**
 * Created by hilfritz on 26/11/16.
 * http://stackoverflow.com/questions/32965790/retrofit-2-0-how-to-print-the-full-json-response
 * https://github.com/square/retrofit/issues/1072#
 */

public class LoggingInterceptor implements Interceptor {
    private static final String TAG = "LoggingInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Log.i(TAG, "inside intercept callback");
        Request request = chain.request();
        long t1 = System.nanoTime();
        HttpUrl url = request.url();
        String requestLog = String.format("Sending request to %s on %s%n%s",
                url, chain.connection(), request.headers());
        if (request.method().compareToIgnoreCase("post") == 0) {
            requestLog = "\n" + requestLog + "\n" + bodyToString(request);
        }
        //Logger.d(TAG, "#REQ#POST#URL:"+ requestLog);
        Log.d(TAG, "#REQ#POST#URL:"+ requestLog);
        Response response = chain.proceed(request);
        long t2 = System.nanoTime();


        String responseLog = String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers());
        String bodyStringOnly = response.body().string();
        //Logger.d(TAG,"#RSP#POST#URL:" +url.toString()+" #values:\n" + bodyStringOnly);
        Log.d(TAG, "#RSP#POST#URL:" +url.toString()+" #values:\n" + bodyStringOnly);

        return response.newBuilder()
                .body(ResponseBody.create(response.body().contentType(), bodyStringOnly))
                .build();
    }


    public static String bodyToString(final Request request) {
        try {
            final Request copy = request.newBuilder().build();
            final Buffer buffer = new Buffer();
            copy.body().writeTo(buffer);
            return buffer.readUtf8();
        } catch (final IOException e) {
            return "did not work";
        }
    }
}