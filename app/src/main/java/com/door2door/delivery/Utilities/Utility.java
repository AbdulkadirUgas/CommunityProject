package com.door2door.delivery.Utilities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentTransaction;

import com.door2door.delivery.R;
import com.door2door.delivery.fragment.DetailFragment;

import static com.door2door.delivery.Utilities.SharedPref.setCartCount;

public class Utility {
    public static void HideKeyboard(Activity context){
        context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    public static void countUpdate(DatabaseHelper db, Activity sActivity){
        int count=0;
        Cursor res=db.getCartCount();
        while (res.moveToNext()) {
            String count1 = res.getString(0);
            count=Integer.parseInt(count1);
        }
        setCartCount(count,sActivity);
        Intent intent =sActivity.getIntent();
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        sActivity.overridePendingTransition(0,0);
        sActivity.invalidateOptionsMenu();
    }

    /*Initialize Toolbar*/
    public static void setCartCountToolbar(Activity sActivity, TextView tv_cartCount, FrameLayout frameLayout){
        if (SharedPref.getCartCount(sActivity)>0){
            frameLayout.setVisibility(View.VISIBLE);
            tv_cartCount.setText(""+SharedPref.getCartCount(sActivity));
        }
    }

    //check if item item is Already on cart
    public static boolean isOnCart(String itemID, DatabaseHelper db){
       return db.getItemByID(itemID);
    }
}
