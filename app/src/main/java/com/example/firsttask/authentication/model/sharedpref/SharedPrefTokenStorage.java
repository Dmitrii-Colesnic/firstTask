package com.example.firsttask.authentication.model.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefTokenStorage {

    private static final String SHARED_PREFS_NAME = "shared_prefs_name";
    private static final String KEY_TOKEN = "token";
    public static final String DEFAULT_VALUE = "no token";

    private SharedPreferences sharedPreferences;

    public SharedPrefTokenStorage(Context context){
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void save(String token){
        sharedPreferences.edit().putString(KEY_TOKEN, token).apply();
    }

    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, DEFAULT_VALUE);
    }

    public void delete() {
        sharedPreferences.edit().clear().commit();
    }

}
