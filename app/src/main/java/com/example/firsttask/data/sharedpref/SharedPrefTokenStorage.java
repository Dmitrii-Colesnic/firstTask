package com.example.firsttask.data.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPrefTokenStorage {

    private static final String SHARED_PREFS_NAME = "shared_prefs_name";

    private static final String KEY_TOKEN = "token";
    public static final String DEFAULT_VALUE = "no token";

    private static final String KEY_LANGUAGE = "language";
    private static final String DEFAULT_LANGUAGE = "en";

    private static final String KEY_START_DATE = "startDate";
    private static final String KEY_END_DATE = "endDate";
    public static final String DEFAULT_DATE = "none";

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


    public void saveStartDate(String startDate){
        sharedPreferences.edit().putString(KEY_START_DATE, startDate).apply();
    }
    public String getStartDate(){
        return sharedPreferences.getString(KEY_START_DATE, DEFAULT_DATE);
    }
    public void removeStartDate(){
        sharedPreferences.edit().remove(KEY_START_DATE).apply();
    }


    public void saveEndDate(String endDate){
        sharedPreferences.edit().putString(KEY_END_DATE, endDate).apply();
    }
    public String getEndDate(){
        return sharedPreferences.getString(KEY_END_DATE, DEFAULT_DATE);
    }
    public void removeEndDate(){
        sharedPreferences.edit().remove(KEY_END_DATE).apply();
    }
}
