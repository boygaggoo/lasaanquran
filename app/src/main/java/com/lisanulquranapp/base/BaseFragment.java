package com.lisanulquranapp.base;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import com.lisanulquranapp.network.NetworkController;
import com.lisanulquranapp.utils.SessionController;


/**
 * @author Nauman Ashraf.
 */
public abstract class BaseFragment extends Fragment {

    protected NetworkController mNetworkController = NetworkController.getInstance();
    protected SessionController mSessionController = SessionController.getInstance();

    protected void doStartActivity(Context context, Class cls) {
        Intent intent = new Intent(context, cls);
        startActivity(intent);
    }

    protected String getColoredSpanned(String text, String color) {
        String input = "<font color=" + color + ">" + text + "</font>";
        return input;
    }

}
