package com.example.simple_soul.keepsecret.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/5/11.
 */

public class PreUtils
{

    public static String getSafe(Context context, String safe, String defValue)
    {
        SharedPreferences preferences = context.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        return preferences.getString(safe, defValue);
    }

    public static void setSafe(Context context, String safe, String value)
    {
        SharedPreferences preferences = context.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        preferences.edit().putString(safe, value).commit();
    }

    public static void setString(Context context, String safe, String key, String value)
    {
        SharedPreferences preferences = context.getSharedPreferences(safe, Context.MODE_PRIVATE);
        preferences.edit().putString(key, value).commit();
    }

    public static String getString(Context context, String safe, String key, String defValue)
    {
        SharedPreferences preferences = context.getSharedPreferences(safe,
                Context.MODE_PRIVATE);
        return preferences.getString(key, defValue);
    }

    public static void removeSafe(Context context, String safe)
    {
        SharedPreferences preferences = context.getSharedPreferences("config",
                Context.MODE_PRIVATE);
        preferences.edit().remove(safe).commit();
        SharedPreferences preferences1 = context.getSharedPreferences(safe,
                Context.MODE_PRIVATE);
        preferences1.edit().clear().commit();
    }

    public static void removeKey(Context context, String safe, String key)
    {
        SharedPreferences preferences = context.getSharedPreferences(safe, Context.MODE_PRIVATE);
        preferences.edit().remove(key).commit();
    }
}
