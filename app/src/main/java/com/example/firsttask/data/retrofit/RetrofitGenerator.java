package com.example.firsttask.data.retrofit;

import android.content.Context;

import androidx.annotation.NonNull;

import com.example.firsttask.data.sharedpref.SharedPrefTokenStorage;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitGenerator {

    private static RetrofitGenerator retrofitGeneratorInstance = null;

    public Retrofit retrofitInstance;

    private static final String BASE_URL = "https://pop-merchant-test-tr.paynet.md";

    private RetrofitGenerator(Context context) {
        retrofitInstance = generateRetrofit(context);
    }

    public static RetrofitGenerator getRetrofit(Context context){
        if(retrofitGeneratorInstance == null){
            retrofitGeneratorInstance = new RetrofitGenerator(context);
        }

        return retrofitGeneratorInstance;
    }

//    public static void deleteRetrofit(){
//        if(retrofitGeneratorInstance != null){
//            retrofitGeneratorInstance = null;
//        }
//    }

    private Retrofit generateRetrofit(Context context/*, String language*/) {

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new Interceptor() {
                    @NonNull
                    @Override
                    public Response intercept(@NonNull Chain chain) throws IOException {
                        Request original = chain.request();

                        Request request = original.newBuilder()
                                .header("Authorization", String.format("bearer %s", new SharedPrefTokenStorage(context).getToken()))
                                .header("Accept-Language", new SharedPrefTokenStorage(context).getLanguage())
                                .build();

                        return chain.proceed(request);
                    }
                })
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit;

    }

}
