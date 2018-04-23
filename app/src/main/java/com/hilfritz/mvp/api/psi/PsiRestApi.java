package com.hilfritz.mvp.api.psi;

import com.hilfritz.mvp.api.LoggingInterceptor;
import com.hilfritz.mvp.api.psi.pojo.PsiPojo;

import java.util.concurrent.TimeUnit;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

public class PsiRestApi implements PsiRestInterface {
    private static final String BASE_URL = "https://api.data.gov.sg";
    private static final String VERSION_URL = BASE_URL+"/v1";
    private static final String ENVIRONMENT_URL = VERSION_URL+"/environment";
    public static final String PSI_URL = ENVIRONMENT_URL+"/psi";

    PsiRestInterface api;
    Retrofit retrofit;

    public PsiRestApi() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        okhttp3.OkHttpClient client = new okhttp3.OkHttpClient().newBuilder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(new LoggingInterceptor())
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())  //enable RXJAVA
                .build();
        api = retrofit.create(PsiRestInterface.class);
    }

    @Override
    public Observable<PsiPojo> getAllPsi() {
        return api.getAllPsi();
    }
}
