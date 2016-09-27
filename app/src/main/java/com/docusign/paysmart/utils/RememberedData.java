package com.docusign.paysmart.utils;

import android.content.SharedPreferences;

import com.docusign.paysmart.PaySmartApplication;

/**
 * Created by krshah on 9/24/16.
 */

public class RememberedData {

    private static final String TAG = RememberedData.class.getCanonicalName();
    private static final String USER_EMAIL = "userEmail";
    private static SharedPreferences mSharedPrefs = PaySmartApplication.getSharedPreferences();

    public static String getStoredEmail() {
        return mSharedPrefs.getString(USER_EMAIL, "");
    }

    public static void setUserEmail(String userEmail) {
        mSharedPrefs.edit().putString(USER_EMAIL, userEmail).apply();
    }
}
