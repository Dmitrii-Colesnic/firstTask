package com.example.firsttask.data.sharedpref;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefFirstLaunch {

    private static final String SHARED_PREFS_NAME = "SharedPrefFirstLaunch";
    private static final String KEY_FIRST_LAUNCH = "keyFirstLaunch";
    private static final String FIRST_LAUNCH = "firstLaunch";

    private final SharedPreferences sharedPreferences;

    public SharedPrefFirstLaunch(Context context) {this.sharedPreferences = context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);}

    public void save(){sharedPreferences.edit().putString(KEY_FIRST_LAUNCH, "first launch was successful").apply();}

    public boolean firstLaunch(){return FIRST_LAUNCH.equals(sharedPreferences.getString(KEY_FIRST_LAUNCH, FIRST_LAUNCH));}

}
