package com.docusign.paymentgo.utils;

import android.content.SharedPreferences;

import com.docusign.paymentgo.PaymentGoApplication;

/**
 * Created by krshah on 9/24/16.
 */

public class RememberedData {

    private static final String TAG = RememberedData.class.getCanonicalName();

    private static SharedPreferences mSharedPrefs = PaymentGoApplication.getSharedPrefs();

    private static final String USER_EMAIL = "userEmail";

    public static String getStoredEmail() {
        return mSharedPrefs.getString(USER_EMAIL, "");
    }

    public static void setUserEmail(String userEmail) {
        mSharedPrefs.edit().putString(USER_EMAIL, userEmail).apply();
    }
}
