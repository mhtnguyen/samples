package com.example.minhhung.tribalscale.api;

import android.content.Context;

import com.example.minhhung.tribalscale.util.InternetConnection;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by minhhung on 2/5/2017.
 */
public class Client {

    /********
     * URLS
     *******/
    private static final String ROOT_URL = "https://randomuser.me/";

    /**
     * Get Retrofit Instance
     */

    private final Context context;

    public Client(Context context) {
        this.context = context;
    }

    public Retrofit getRetrofitInstance() {
        File httpCacheDirectory = new File(context.getCacheDir(), "responses");

        Cache cache = null;
        cache = new Cache(httpCacheDirectory, 10 * 1024 * 1024);

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        //okhttp provides support for cache control headers
        httpClient.cache(cache);

        httpClient.addInterceptor(logging);
        /*max-age is the oldest that a response can be, as long as the Cache-Control from the origin server indicates that it is still fresh.
        max-stale indicates that, even if the response is known to be stale, you will also accept it as long as it's only stale by that number of seconds.
         */
        httpClient.addInterceptor(new Interceptor() {
            @Override public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (InternetConnection.checkConnection(context)) {
                    request = request.newBuilder().header("Cache-Control", "public, max-age=" + 60).build();
                } else {
                    request = request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build();
                }
                return chain.proceed(request);
            }
        })
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        return retrofit;
    }

    /**
     * Get API Service
     *
     * @return API Service
     */
    public ApiService getApiService() {
        return getRetrofitInstance().create(ApiService.class);
    }
}
