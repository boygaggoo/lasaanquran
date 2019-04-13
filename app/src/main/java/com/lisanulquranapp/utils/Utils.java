package com.lisanulquranapp.utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.inputmethod.InputMethodManager;

import com.lisanulquranapp.models.ChaptersModel;

public class Utils {

    public static final int NETWORK_LIMIT = 20;
    public static Bitmap photoBitmap = null;
    public static String photoPath = "";

    public static void hideSoftKeyboard(Activity activity) {
        try {
            InputMethodManager inputMethodManager =
                    (InputMethodManager) activity.getSystemService(
                            Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
