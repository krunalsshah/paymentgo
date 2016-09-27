package com.docusign.paysmart;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDexApplication;

/**
 * Created by krshah on 9/24/16.
 */

public class PaySmartApplication extends MultiDexApplication {
    private static final String TAG = PaySmartApplication.class.getCanonicalName();

    private static SharedPreferences sSharedPrefs;

    public static SharedPreferences getSharedPreferences() {
        return sSharedPrefs;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }
}
