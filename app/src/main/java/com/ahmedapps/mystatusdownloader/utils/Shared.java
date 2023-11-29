package com.ahmedapps.mystatusdownloader.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Shared {

    private static SharedPreferences mPreferences;

    private static SharedPreferences getInstance(Context context) {
        if (mPreferences == null) {
            mPreferences = context.getApplicationContext()
                    .getSharedPreferences("my_app_shared_app_file_ahmed_gdm_app", Context.MODE_PRIVATE);
        }
        return mPreferences;
    }


    // ---------------


    public static String getString(Context context, String key, String defaultValue) {
        return getInstance(context).getString(key, defaultValue);
    }

    public static void setString(Context context, String key, String value) {
        getInstance(context).edit().putString(key, value).apply();
    }


    // ---------------


    public static int getInt(Context context, String key, int defaultValue) {
        return getInstance(context).getInt(key, defaultValue);
    }

    public static void setInt(Context context, String key, int value) {
        getInstance(context).edit().putInt(key, value).apply();
    }


    // ---------------


    public static Boolean getBoolean(Context context, String key, boolean defaultValue) {
        return getInstance(context).getBoolean(key, defaultValue);
    }

    public static void setBoolean(Context context, String key, boolean value) {
        getInstance(context).edit().putBoolean(key, value).apply();
    }
}
