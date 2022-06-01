package com.eliteinfotech.vewash;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefHelper {

    Context context;
    String name;
    SharedPreferences sharedPref;
    SharedPreferences.Editor sharedEditor;

    public SharedPrefHelper(String name, Context context) {
        this.context = context;
        this.name = name;
        this.sharedPref = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        this.sharedEditor = context.getSharedPreferences(name, Context.MODE_PRIVATE).edit();
    }

    public String getString(String key) {
        return sharedPref.getString(key, "");
    }

    public boolean getBool(String key) {
        return sharedPref.getBoolean(key, false);
    }

    public void setString(String key, String value) {
        sharedEditor.putString(key, value);
        sharedEditor.apply();
    }

    public void setBool(String key, boolean value) {
        sharedEditor.putBoolean(key, value);
        sharedEditor.apply();
    }

    public void setInt(String key, int value) {
        sharedEditor.putInt(key, value);
        sharedEditor.apply();
    }

    public static void removeShared(String key,Context context) {
        SharedPreferences settings = context.getSharedPreferences(key, Context.MODE_PRIVATE);
        settings.edit().clear().commit();
    }

}

