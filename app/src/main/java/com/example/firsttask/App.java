package com.example.firsttask;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

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

    public static boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
