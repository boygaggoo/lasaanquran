package com.ctandem.lasaanulquran.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ctandem.lasaanulquran.R;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Nauman Ashraf on 19/06/2017.
 */
public class StaticMethod {

    private static final String TAG = StaticMethod.class.getCanonicalName();
    public static final int PICK_CONTACT = 101;
    public static String pound = "\u00a3";


    /**
     * @return
     */
    public static boolean isExerternalStorageAvailable() {
        return Environment.getExternalStorageState().contentEquals(
                Environment.MEDIA_MOUNTED) ? true : false;
    }

    public static String dateToDateStr(Date pDate) {
        String dateStr = "";

        try {
            SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");
            dateStr = myFormat.format(pDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     * @param pDate
     * @return
     */
    public static Date convertStringTODate(final String pDate) {
        Date date = null;

        try {
            SimpleDateFormat myFormat = new SimpleDateFormat(
                    "dd/MM/yyyy");
            date = myFormat.parse(pDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * @param pDate
     * @return
     */
    public static Date convertStringDate(final String pDate) {
        Date date = null;

        try {
            SimpleDateFormat myFormat = new SimpleDateFormat(
                    "MM/dd/yyyy");
            date = myFormat.parse(pDate);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * This method is used to check the Internet connection. Its
     * Available or not.
     *
     * @param context
     * @return boolean
     */
    public static boolean checkInternetConnection(Context context) {

        try {
            ConnectivityManager conMgr = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception exc) {
            Log.e(TAG, "Error occure while checking internet connection" + exc);
        }
        return false;
    }

    public static boolean checkMobileDataEnabled(Context context) {
        boolean mobileDataEnabled = false; // Assume disabled
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        try {
            Class cmClass = Class.forName(cm.getClass().getName());
            Method method = cmClass.getDeclaredMethod("getMobileDataEnabled");
            method.setAccessible(true); // Make the method callable
            // get the setting for "mobile data"
            mobileDataEnabled = (Boolean) method.invoke(cm);
        } catch (Exception e) {
            // Some problem accessible private API
            // TODO do whatever error handling you want here
        }
        return mobileDataEnabled;
    }

  /*  public static void logoutProcess(Context context) {
        LoginManager.getInstance().logOut();
        PreferenceHandler.getInstance(context).removeAllValues();
        DatabaseHelper.getInstance(context).clearAllTable();
        SessionController.getInstance().setUserModel(null);

        Intent intent = new Intent(context, AccountActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
//        ((SettingActivity)context).finish();
    }*/

    /**
     * This method is used to check the Internet connection. Its
     * Available or not.
     *
     * @param context
     * @return boolean
     */
    public static void checkInternetConnectionValidation(Context context) {

        try {
            ConnectivityManager conMgr = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (conMgr.getActiveNetworkInfo() != null
                    && conMgr.getActiveNetworkInfo().isAvailable()
                    && conMgr.getActiveNetworkInfo().isConnected()) {
                //return true;
                showMessageOk(context, context.getString(R.string.NO_INTERNET_CONNECTIVITY_HEAD),
                        context.getString(R.string.NO_INTERNET_CONNECTIVITY), false);

            } else {
                //return false;
            }
        } catch (Exception exc) {
            Log.e(TAG, "Error occure while checking internet connection" + exc);
        }
    }

    public static String dateFormate(String dateO) {
        String dateFormat = dateO;
        try {
            Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dateFormat);
            SimpleDateFormat sdf = new SimpleDateFormat("E dd, MMM, yyyy");
            dateFormat = sdf.format(date);
        } catch (Exception e) {
            Log.e("DateFormat Ex", e.toString());
        }
        return dateFormat;
    }

    public static void alertDialog(final Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        //((Activity) context).finish();
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    /**
     * This method is used to show the alert dialog box and show specific message
     * in case of success or failure.
     *
     * @param context
     * @param Title
     * @param Message
     * @param success
     */
    public static void showMessageOk(final Context context,
                                     final String Title, final String Message, final boolean success) {

        View view = View.inflate(context, R.layout.message_dialog_view, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setView(view);
        TextView title = view.findViewById(R.id.dialog_title);
        TextView message = view.findViewById(R.id.dialog_message);
        TextView button = view.findViewById(R.id.dialog_btn);
        final Dialog dialog = builder.create();
        dialog.show();
        if (!Title.isEmpty()) {
            title.setText(Title);
        }
        message.setText(Message);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                if (success) {
                    ((Activity) context).finish();
                }
            }
        });
    }

    /**
     * @param context
     * @param titleView
     * @return
     */
    public static TextView getTitleInCenter(final Context context, final String titleView) {

        TextView mesageTitle = new TextView(context);
        mesageTitle.setTextSize(20);
        mesageTitle.setText(titleView);
        mesageTitle.setTextColor(Color.BLACK);
        mesageTitle.setGravity(Gravity.CENTER);
        mesageTitle.setTypeface(null, Typeface.BOLD);
        mesageTitle.setPadding(0, 5, 0, 5);

        return mesageTitle;
    }

    /**
     * @param context
     * @param msgView
     */
    public static void showToast(final Context context, final String msgView) {

        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(context, msgView, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public static void showLongTostMsg(final Context context, final String msgView) {

        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {

            @Override
            public void run() {
                Toast.makeText(context, msgView, Toast.LENGTH_LONG).show();
            }
        });
    }

    public static void startActivity(Activity activity, Context context, Class<?> cls) {

        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
        activity.overridePendingTransition(R.anim.enter, R.anim.exit);
    }

    public static void startActivityAndFinish(Activity activity, Context context, Class<?> cls) {

        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
        activity.overridePendingTransition(R.anim.enter, R.anim.exit);
        activity.finish();
    }


    public final static boolean isValidEmail(CharSequence target) {
        if (target == null)
            return false;
        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    /***
     *
     * @param fbUserID
     * @return
     */
    public static String getFbDPPicturePath(String fbUserID) {

        String photoUrl = "";
        try {
            photoUrl = "https://graph.facebook.com/" + fbUserID + "/picture?type=large";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return photoUrl;
    }


    public static String getDateString(long time) {

        /*Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(time);
        String date = DateFormat.format("dd-MM-yyyy", cal).toString();
        return date;*/

        long dv = time * 1000;// its need to be in milisecond
        Date df = new Date(dv);
        String vv = new SimpleDateFormat("dd-MM-yyyy").format(df);
        return vv;
    }

//    public static void maintainSession() {
//        if(SessionController.getInstance().getUserModel() == null){
//            if(PreferenceHandler.getInstance(AppController.getInstance()).getUserId()!= 0){
//                UserController controller = new UserController();
//                if (controller.isUserAvaiable(PreferenceHandler.getInstance(AppController.getInstance()).getUserId())){
//                    UserModel userModel = controller.getUserById(PreferenceHandler.getInstance(AppController.getInstance()).getUserId());
//                    SessionController mSessionController = SessionController.getInstance();
//                    mSessionController.setUserModel(userModel);
//                    return;
//                }
//            }
//        }
//    }

    public static void changeToolbarFont(Toolbar toolbar, Activity context) {
        for (int i = 0; i < toolbar.getChildCount(); i++) {
            View view = toolbar.getChildAt(i);
            if (view instanceof TextView) {
                TextView tv = (TextView) view;
                if (tv.getText().equals(toolbar.getTitle())) {
                    applyFont(tv, context);
                    break;
                }
            }
        }
    }

    public static void applyFont(TextView tv, Activity context) {
        tv.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/jameel_noori"));
    }
}
