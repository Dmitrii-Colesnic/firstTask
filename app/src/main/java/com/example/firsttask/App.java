package com.example.firsttask;

import android.app.Application;
import android.content.Context;

import com.example.firsttask.data.retrofit.RetrofitGenerator;
import com.example.firsttask.data.retrofit.UserService;

import io.reactivex.plugins.RxJavaPlugins;
import retrofit2.Retrofit;

public class App extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }

    public UserService getUserService() {
        Retrofit retrofit = RetrofitGenerator.getRetrofit(mContext).retrofitInstance;
        UserService userService = retrofit.create(UserService.class);
        return userService;
    }

}
