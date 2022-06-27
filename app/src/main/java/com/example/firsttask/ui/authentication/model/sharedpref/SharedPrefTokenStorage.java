package com.example.firsttask.ui.authentication.model.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefTokenStorage {

    private static final String SHARED_PREFS_NAME = "shared_prefs_name";

    private static final String KEY_TOKEN = "token";
    public static final String DEFAULT_VALUE = "no token";

    private static final String KEY_LANGUAGE = "language";
    private static final String DEFAULT_LANGUAGE = "en";

    private SharedPreferences sharedPreferences;

    public SharedPrefTokenStorage(Context context){
        this.sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    public void saveToken(String token){
        sharedPreferences.edit().putString(KEY_TOKEN, token).apply();
    }

    public String getToken() {
        return sharedPreferences.getString(KEY_TOKEN, DEFAULT_VALUE);
    }

    public void deleteToken() {
        sharedPreferences.edit().remove(KEY_TOKEN).apply();
    }

    public void saveLanguage(String language){
        sharedPreferences.edit().putString(KEY_LANGUAGE, language).apply();
    }

    public String getLanguage() {
        return sharedPreferences.getString(KEY_LANGUAGE, DEFAULT_LANGUAGE);
    }


}
