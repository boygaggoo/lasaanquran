package com.ctandem.lasaanulquran.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.ctandem.lasaanulquran.network.NetworkController;
import com.ctandem.lasaanulquran.preferences.Preference;
import com.ctandem.lasaanulquran.utils.SessionController;

import org.json.JSONArray;

/**
 * @author Nauman Ashraf.
 */

public abstract class BaseActivity extends AppCompatActivity /*implements View.OnClickListener*/ {

    protected NetworkController mNetworkController = NetworkController.getInstance();
    protected SessionController mSessionController = SessionController.getInstance();

    JSONArray jsonArray = new JSONArray();

    Preference preference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setWindowDirection();
    }

    protected void setWindowDirection() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            if (getWindow().getDecorView().getLayoutDirection() == View.LAYOUT_DIRECTION_LTR) {
                getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
            }
        }
    }

//    protected void setLanguage() {
//
//        Resources res = getResources();
//        preference = new Preference(this);
//        String lang = preference.getStringPrefrence(res.getString(R.string.language), "en");
//        DisplayMetrics metrics = res.getDisplayMetrics();
//        Configuration configuration = res.getConfiguration();
//        Locale locale = new Locale(lang.toLowerCase());
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            configuration.setLocale(locale);
//            res.updateConfiguration(configuration, metrics);
//        } else {
//            Toast.makeText(this, "Your mobile does not support multi language selection.",
//                    Toast.LENGTH_SHORT).show();
//        }
//    }

    @Override
    protected void onResume() {
        /*if(SessionController.getInstance().getUserModel()==null){
            maintainLogin();
        }*/
        super.onResume();
    }

//    private void maintainLogin() {
//        if (PreferenceHandler.getInstance(this).getUserId() != 0) {
//            UserController controller = new UserController();
//            if (controller.isUserAvaiable(PreferenceHandler.getInstance(this).getUserId())) {
//                List<UserModel> ncUserList = controller.getAllUsers();
//                UserModel ncUser = ncUserList.get(0);
//                SessionController mSessionController = SessionController.getInstance();
//                mSessionController.setUserModel(ncUser);
//                //Intent intent = new Intent(this, HomeActivity2.class);
//                //startActivity(intent);
//                //this.finish();
//                //return;
//            }
//        }
//    }

    //public abstract void syncMessage(String message);

}
