package com.docusign.paymentgo.activity;

import android.os.Bundle;
import android.os.Handler;

import com.docusign.paymentgo.R;

public class SplashActivity extends BaseActivity {

    private static final String TAG = SplashActivity.class.getCanonicalName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Handler h = new Handler();
        //Delay to simulate n/w call
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                launchSetup();
            }
        }, 500);
    }

    private void launchSetup() {
        startActivity(LoginActivity.createIntent(this));
        this.finish();
    }
}