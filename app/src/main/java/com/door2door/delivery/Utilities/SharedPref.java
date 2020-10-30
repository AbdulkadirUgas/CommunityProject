package com.door2door.delivery.Utilities;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    public static void setCartCount(int count, Activity sActivity) {
        SharedPreferences pref = sActivity.getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("CartCount",count);
        editor.apply();
    }

    public static int getCartCount(Context sActivity) {
        SharedPreferences pref = sActivity.getSharedPreferences("MyPref", 0);
        return pref.getInt("CartCount", 0);
    }
}
