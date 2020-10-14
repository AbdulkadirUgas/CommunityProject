package com.door2door.Utilities;

import android.app.Activity;
import android.view.WindowManager;

public class Utility {
    public static void HideKeyboard(Activity context){
        context.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }
}
