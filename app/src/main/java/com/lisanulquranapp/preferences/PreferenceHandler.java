package com.lisanulquranapp.preferences;

import android.content.Context;

/**
 * @author Nauman Ashraf.
 */
public class PreferenceHandler {

    private static PreferenceHandler instance;

    private Context context;
    private Preference preference = null;

    private final String USER_ID_PREF = "user_id_preference";
    private final String IS_LOGIN = "is_login";

    private long userId = 0;
    private String fbUserId = "";
    private boolean isLogin = false;

    public PreferenceHandler(Context pContext) {
        this.context = pContext;
        preference = new Preference(context);
    }

    public PreferenceHandler() {

    }

    /**
     * @return the instance
     */
    public static PreferenceHandler getInstance(Context pContext) {
        if (instance == null) {
            instance = new PreferenceHandler(pContext);
        }
        return instance;
    }

    public long getUserId() {
        userId = preference.getLongPrefrence(USER_ID_PREF, 0);
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
        preference.saveIntegerLongPrefrence(USER_ID_PREF, this.userId);
    }

    public boolean isLogin() {
        this.isLogin = preference.getBooleanFlagPrefrence(IS_LOGIN, false);
        return this.isLogin;
    }

    public void setLogin(boolean login) {
        this.isLogin = login;
        preference.saveBooleanFlagInPrefrence(IS_LOGIN, isLogin);
    }

    public void removeAllValues() {
        setUserId(0);
        setFbUserId("");
        //setLocationSet(false);
    }

    public String getFbUserId() {
        return fbUserId;
    }

    public void setFbUserId(String fbUserId) {
        this.fbUserId = fbUserId;
    }

    public void setString(String key, String value) {
        preference.saveStringInPrefrence(key, value);
    }

    public String getString(String key, String value) {
        return preference.getStringPrefrence(key, value);
    }
}
