package com.ctandem.lasaanulquran.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * @author Nauman Ashraf.
 */
public class Preference {

    private static final String TAG = Preference.class.getCanonicalName();
    private String PrefName = "LasaanulQuran";

    private Context context = null;
    private SharedPreferences pref = null;

    public Preference(final Context pContext) {
        this.context = pContext;
        pref = context.getSharedPreferences(PrefName, Context.MODE_PRIVATE);
    }

    public void saveStringInPrefrence(final String pRef, final String value) {

        try {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString(pRef, value);
            editor.commit();
        } catch (Exception e) {
            Log.e(TAG, "" + e.getMessage());
        }
    }

    public void saveBooleanFlagInPrefrence(final String key, final boolean value) {

        try {
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean(key, value);
            editor.commit();
        } catch (Exception e) {
            Log.e(TAG, "" + e.getMessage());
        }
    }

    public boolean getBooleanFlagPrefrence(final String key, final boolean defaultVal) {
        return pref.getBoolean(key, defaultVal);
    }

    /**
     * @param pRef
     * @param defaultValue
     * @return String
     */
    public String getStringPrefrence(final String pRef,
                                     final String defaultValue) {
        return pref.getString(pRef, defaultValue);
    }

    /**
     * @param pRef
     * @param value
     */
    public void saveIntegerInPrefrence(final String pRef, final int value) {

        try {
            SharedPreferences.Editor editor = pref.edit();
            editor.putInt(pRef, value);
            editor.commit();
        } catch (Exception e) {
            Log.e(TAG, "" + e.getMessage());
        }
    }

    /**
     * @param pRef
     * @param defaultValue
     * @return String
     */
    public int getIntegerPrefrence(final String pRef, final int defaultValue) {
        return pref.getInt(pRef, defaultValue);
    }

    /**
     * @param pRef
     * @param value
     */
    public void saveIntegerLongPrefrence(final String pRef, final long value) {

        try {
            SharedPreferences.Editor editor = pref.edit();
            editor.putLong(pRef, value);
            editor.commit();
        } catch (Exception e) {
            Log.e(TAG, "" + e.getMessage());
        }
    }

    /**
     * @param pRef
     * @param defaultValue
     * @return long
     */
    public long getLongPrefrence(final String pRef, final long defaultValue) {
        return pref.getLong(pRef, defaultValue);
    }


}