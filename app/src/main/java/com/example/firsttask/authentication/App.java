package com.example.firsttask.authentication;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.firsttask.authentication.model.retrofit.RetrofitGenerator;
import com.example.firsttask.authentication.model.retrofit.UserService;

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
