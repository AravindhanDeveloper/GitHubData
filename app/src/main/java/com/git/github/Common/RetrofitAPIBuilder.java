package com.git.github.Common;


import com.facebook.stetho.okhttp3.StethoInterceptor;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAPIBuilder {
  static Retrofit retrofit = null;

  public static synchronized Retrofit getInstance() {

    final HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    final OkHttpClient okHttpClient = new OkHttpClient.Builder()
      .addInterceptor(loggingInterceptor)
      .protocols(Arrays.asList(Protocol.HTTP_1_1))
      .readTimeout(220, TimeUnit.SECONDS)
      .connectTimeout(180, TimeUnit.SECONDS)
      .addNetworkInterceptor(new StethoInterceptor())
      .build();

    if (retrofit == null) {
      retrofit = new Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build();
    }
    return retrofit;
  }
}
