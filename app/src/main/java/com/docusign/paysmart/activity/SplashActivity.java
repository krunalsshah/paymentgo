package com.docusign.paysmart.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.widget.ImageView;

import com.docusign.paysmart.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

    private static final String TAG = SplashActivity.class.getCanonicalName();

    @BindView(R.id.ivAppLogo)
    ImageView mIvAppLogo;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        rotateCircular(mIvAppLogo, Animation.INFINITE);
        Handler h = new Handler();
        //Delay to simulate n/w call
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                launchSetup();
            }
        }, 1000);
    }

    private void launchSetup() {
        startActivity(LoginActivity.createIntent(this));
        this.finish();
    }
}