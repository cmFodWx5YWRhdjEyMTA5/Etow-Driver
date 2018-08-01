package com.app.etow.data.networking;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import com.app.etow.BuildConfig;
import com.app.etow.constant.Constant;
import com.app.etow.constant.KeyAPI;
import com.app.etow.models.response.ApiResponse;
import com.app.etow.models.response.ApiSuccess;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import io.realm.annotations.Ignore;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface EtowService {

    class Creator {
        public static Retrofit newRetrofitInstance() {
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
            okBuilder.readTimeout(30, TimeUnit.SECONDS);
            okBuilder.connectTimeout(30, TimeUnit.SECONDS);
            okBuilder.retryOnConnectionFailure(true);
            okBuilder.addInterceptor(chain -> {
                Request original = chain.request();
                Request.Builder builder = original.newBuilder();
                builder.header(KeyAPI.KEY_DEVICE_TYPE, KeyAPI.VALUE_DEVICE_TYPE)
                        .header(KeyAPI.KEY_HTTP_X_APP_ID, KeyAPI.VALUE_HTTP_X_APP_ID)
                        .header(KeyAPI.KEY_HTTP_X_SECRET_KEY, KeyAPI.VALUE_HTTP_X_SECRET_KEY)
                        .method(original.method(), original.body());
                return chain.proceed(builder.build());
            });
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
                interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                okBuilder.addInterceptor(interceptor);
            }
            Gson gson = new GsonBuilder()
                    .addDeserializationExclusionStrategy(new ExclusionStrategy() {
                        @Override
                        public boolean shouldSkipField(FieldAttributes f) {
                            return f.getAnnotation(Ignore.class) != null;
                        }

                        @Override
                        public boolean shouldSkipClass(Class<?> clazz) {
                            return false;
                        }
                    }).setDateFormat("yyyy-MM-dd HH:mm:ss")
                    .create();

            return new Retrofit.Builder()
                    .baseUrl(Constant.HOST)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okBuilder.build())
                    .build();
        }
    }

    @FormUrlEncoded
    @POST("user/login")
    Observable<ApiResponse> login(@Field(KeyAPI.KEY_EMAIL) String email,
                                  @Field(KeyAPI.KEY_PASSWORD) String password);

    @FormUrlEncoded
    @POST("user/logout")
    Observable<ApiSuccess> logout(@Field(KeyAPI.KEY_TOKEN) String token);

    @FormUrlEncoded
    @POST("feedback/send")
    Observable<ApiSuccess> sendFeedback(@Field(KeyAPI.KEY_COMMENT) String comment);
}
