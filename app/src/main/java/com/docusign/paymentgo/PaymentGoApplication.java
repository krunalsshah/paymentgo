package com.docusign.paymentgo;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

/**
 * Created by krshah on 9/24/16.
 */

public class PaymentGoApplication extends MultiDexApplication {
    private static final String TAG = PaymentGoApplication.class.getCanonicalName();

    private static final String SHARED_PREFERENCES_FILENAME = "payments_go_prefs";
    private static SharedPreferences mSharedPrefs;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, ">onCreate");
        initSharedPrefs();
    }

    private void initSharedPrefs() {
        mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    }

    @Override
    public void onLowMemory() {
        Log.d(TAG, ">onLowMemory");
        super.onLowMemory();
    }

    @Override
    public void onTerminate() {
        Log.d(TAG, ">onTerminate");
        super.onTerminate();
    }

    public static SharedPreferences getSharedPrefs() {
        return mSharedPrefs;
    }
}
